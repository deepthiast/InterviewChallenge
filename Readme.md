### Description

You have a simple road topology.
Each Road has a length and a direction (increasing/decreasing).  
It **can be** connected on either end to another Road in the topology.


### Task

Write a function `int getLength(Database db, String id1, int location1, String id2, int location2)` to get the length between two locations (road id + position). The function should return `-1` in error cases.


### Properties

Road:  

|field | type | description |
| --- | --- | --- |
| id | String | identifier |
| length | int | length of the road |
| direction | enum (INCREASING/DECREASING) | direction in a "global" sense, for now always INCREASING |
| connection Begin | String | optional id of the element at the start (DECREASING) side |
| connection End | String | optional id of the element at the end (INCREASING) side |


### Notes

- connections on a road can be "invalid" (empty/null)
- assume there are no loops in the topology
- as a start: assume the direction is coherent, so it won't switch from one road to another


---


#### Topology

![Topo](topo.png)


#### Data

    [
      {"id":"B","length":20000,"direction":"inc","connections":[{"End":"A"}]},
      {"id":"A","length":30000,"direction":"inc","connections":[{"Begin":"B"},{"End":"C"}]},
      {"id":"C","length":13458,"direction":"inc","connections":[{"Begin":"A"},{"End":"K"}]},
      {"id":"K","length":16789,"direction":"inc","connections":[{"Begin":"C"},{"End":"U"}]},
      {"id":"U","length":15972,"direction":"inc","connections":[{"Begin":"K"},{"End":"P"}]},
      {"id":"P","length":3457,"direction":"inc","connections":[{"Begin":"U"}]},
    ]

#### Tests

    Database db;

    EXPECT_EQ(99676, getLength(db, "B", 0, "P", 3457));
    EXPECT_EQ(99676, getLength(db, "P", 3457, "B", 0));
    EXPECT_EQ(50000, getLength(db, "A", 5000, "K", 11542));
    EXPECT_EQ(50000, getLength(db, "K", 11542, "A", 5000));
    EXPECT_EQ(100, getLength(db, "C", 100, "C", 200));
    EXPECT_EQ(100, getLength(db, "C", 200, "C", 100));
    // range checks
    EXPECT_EQ(-1, getLength(db, "C", 99999, "K", 100));
    EXPECT_EQ(-1, getLength(db, "C", 100, "K", 99999));
    EXPECT_EQ(-1, getLength(db, "K", 99999, "C", 100));
    EXPECT_EQ(-1, getLength(db, "K", 100, "C", 99999));
    // invalid items
    EXPECT_EQ(-1, getLength(db, "UNKNOWN", 0, "A", 100));
    EXPECT_EQ(-1, getLength(db, "A", 100, "UNKNOWN", 0));


---


#### Useful functions

##### Direction
    enum Direction { INCREASING, DECREASING };

##### Database

    /**
     * Gets the node with the given id.
     *
     * @param id id of the node to get
     *
     * @return optional road
     */
    public Optional<Road> getById(String id);

    /**
     * Checks if a node with the given id exists.
     * 
     * @param id id of the node to check
     *
     * @return true if the node exists, otherwise false.
     */
    public boolean exists(String id);

    /**
     * Gets the connection Neighbor for the given road.
     *
     * @param road       the road to get the neighbor for
     * @param connection the connection id for the neighbor
     *
     * @return optional road
     */
    public Optional<Road> getNeighborForConnection(Road road, String connection);

    /**
     * Gets the neighbor for the road in relation to the previous road id.
     * Note: the previous id has to exist and be a neighbor to the current road
     *
     * @param road   the road to get the neighbor for
     * @param prevId id of the neighbor to the road node to move opposite from
     *
     * @return optional road
     */
    public Optional<Road> getRoadNeighborFromPrev(Road road, String prevId);

##### Road


    public String getId();
    public Optional<String> getConnection(String index);

    public int getLength();
    public Direction getDirection();
    public Optional<Direction> getDirectionToNodeId(String nodeId);

