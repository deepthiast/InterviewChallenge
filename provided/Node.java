package provided;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

/**
 * Node
 */
public abstract class Node
{
    private final NodeType type;
    private final String id;
    private final Map<String, String> connections;

    public Node(NodeType type, String id, Map<String, String> connections)
    {
        this.type = type;
        this.id = id;
        this.connections = new HashMap<>(connections);
    }

    public NodeType getType()
    {
        return this.type;
    }

    public String getId()
    {
        return this.id;
    }

    public Optional<String> getConnection(String index)
    {
        return Optional.ofNullable(this.connections.get(index));
    }
}
