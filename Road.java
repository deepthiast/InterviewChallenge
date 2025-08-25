package provided;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Road
 */
public final class Road extends Node
{
    private final int length;
    private final Direction direction;

    public Road(String id, int length, Direction direction, Map<String, String> connections)
    {
        super(NodeType.ROAD, id, connections);
        this.length = length;
        this.direction = direction;
    }

    public int getLength()
    {
        return this.length;
    }

    public Direction getDirection()
    {
        return this.direction;
    }

    public Optional<Direction> getDirectionToNodeId(String nodeId)
    {
        if (Objects.equals(nodeId, getConnection("Begin").orElse(null)))
        {
            return Optional.of(Direction.DECREASING);
        }
        else if (Objects.equals(nodeId, getConnection("End").orElse(null)))
        {
            return Optional.of(Direction.INCREASING);
        }
        return Optional.empty();
    }
}
