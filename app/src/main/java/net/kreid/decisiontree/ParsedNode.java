package net.kreid.decisiontree;

/**
 * Created by Kevin on 25/02/2015.
 */
public class ParsedNode {

    private Game.NodeType _nodeType;
    private Game.Direction _direction;
    private String _text;

    public ParsedNode(String text, Game.Direction direction, Game.NodeType nodeType)
    {
        _text = text;
        _direction = direction;
        _nodeType = nodeType;
    }

    public Game.NodeType getType()
    {
        return _nodeType;
    }

    public Game.Direction getDirection()
    {
        return _direction;
    }

    public String getText()
    {
        return _text;
    }
}
