package provided;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Database
 */
public final class Database
{
    private final Map<String, Road> data = new HashMap<>();

    public Database()
    {
        add(new Road("B", 20000, Direction.INCREASING, Map.of("End", "A")));
        add(new Road("A", 30000, Direction.INCREASING, Map.of("Begin", "B", "End", "C")));
        add(new Road("C", 13458, Direction.INCREASING, Map.of("Begin", "A", "End", "K")));
        add(new Road("K", 16789, Direction.INCREASING, Map.of("Begin", "C", "End", "U")));
        add(new Road("U", 15972, Direction.INCREASING, Map.of("Begin", "K", "End", "P")));
        add(new Road("P", 3457, Direction.INCREASING, Map.of("Begin", "U")));
    }

    /**
     * Gets the node with the given id.
     *
     * @param id id of the node to get
     *
     * @return optional road
     */
    public Optional<Road> getById(String id)
    {
        return Optional.ofNullable(this.data.get(id));
    }

    public boolean exists(String id)
    {
        return this.data.containsKey(id);
    }

    public int getSize()
    {
        return this.data.size();
    }

    /**
     * Gets the connection Neighbor for the given road.
     *
     * @param road       the road to get the neighbor for
     * @param connection the connection id for the neighbor
     *
     * @return optional road
     */
    public Optional<Road> getNeighborForConnection(Road road, String connection)
    {
        final Optional<String> neighborId = road.getConnection(connection);
        if (neighborId.isEmpty())
            return Optional.empty();
        return getById(neighborId.get());
    }

    /**
     * Gets the neighbor for the road in relation to the previous road id.
     * Note: the previous id has to exist and be a neighbor to the current road
     *
     * @param road   the road to get the neighbor for
     * @param prevId id of the neighbor to the road nood to move opposite from
     *
     * @return optional road
     */
    public Optional<Road> getRoadNeighborFromPrev(Road road, String prevId)
    {
        Optional<Direction> optDir = road.getDirectionToNodeId(prevId);
        if (optDir.isEmpty())
        {
            return Optional.empty();
        }

        Optional<String> neighborId = Optional.empty();
        if (optDir.get() == Direction.DECREASING)
        {
            // if we come from decreasing direction, go towards the end
            neighborId = road.getConnection("End");
        }
        else if (optDir.get() == Direction.INCREASING)
        {
            // if we come from the increasing direction, go towards the begin
            neighborId = road.getConnection("Begin");
        }

        if (neighborId.isEmpty())
        {
            // no neighbor found
            return Optional.empty();
        }

        final Optional<Road> neighbor = getById(neighborId.get());
        if (neighbor.isEmpty())
        {
            System.out.println("Neighbor for id '" + neighborId.get() + "' not found!");
            return Optional.empty();
        }

        return neighbor;
    }

    private void add(Road entry)
    {
        this.data.put(entry.getId(), entry);
    }
}
