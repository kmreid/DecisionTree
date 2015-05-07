package net.kreid.decisiontree;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 25/02/2015.
 */
public class Node implements Parcelable
{
    private Game.NodeType _nodeType;
    private Game.Direction _direction;
    private String _text;
    private Node _yes;
    private Node _no;

    public Node(String text, Game.Direction direction, Game.NodeType nodeType)
    {
        _text = text;
        _direction = direction;
        _nodeType = nodeType;
    }

    public Game.NodeType getType()
    {
        return _nodeType;
    }
    public void setType(Game.NodeType value)
    {
        _nodeType = value;
    }

    public Game.Direction getDirection()
    {
        return _direction;
    }

    public Node getYes()
    {
        return _yes;
    }
    public void setYes(Node value)
    {
        _yes = value;
    }

    public Node getNo()
    {
        return _no;
    }
    public void setNo(Node value)
    {
        _no = value;
    }

    public String getText()
    {
        return _text;
    }
    public void setText(String value)
    {
        _text = value;
    }

    public Boolean isLeaf()
    {
        return _yes == null && _no == null;
    }

    public Node(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this._nodeType = Game.NodeType.valueOf(data[0]);
        this._direction = Game.Direction.valueOf(data[1]);
        this._text = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this._nodeType.toString(),
                this._direction.toString(),
                this._text});

        // TODO: parcel whole tree
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Node createFromParcel(Parcel in) {
            return new Node(in);
        }

        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
}
