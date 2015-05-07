package net.kreid.decisiontree;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 25/02/2015.
 */

/* Pass-by-reference helper class */
class _<T> {
    T ref;
    public _( T e ){
        ref = e;
    }
    public T g() { return ref; }
    public void s( T e ){ this.ref = e; }

    public String toString() {
        return ref.toString();
    }
}

public class Game
{
    private Node currentNode;
    private Node rootNode;
    private String _temp;
    private List<Direction> _stepsHistory;

    public Game()
    {
        _stepsHistory = new ArrayList<Direction>();
    }

    private String serializeState()
    {
        _<String> state = new _<String>("");

        state.ref += rootNode.getText() + "," + rootNode.getDirection().toString() + "," + rootNode.getType().toString();
        state.ref += System.getProperty("line.separator");

        serializeChildren(rootNode, state);

        String stateStr = state.g().replaceAll(System.getProperty("line.separator") + " $", "");

        return stateStr;
    }

    private void serializeChildren(Node currentNode, _<String> str)
    {
        if(!currentNode.isLeaf())
        {
            str.ref += currentNode.getYes().getText() + "," + currentNode.getYes().getDirection().toString() + "," + currentNode.getYes().getType().toString();
            str.ref += System.getProperty("line.separator");

            str.ref += currentNode.getNo().getText() + "," + currentNode.getNo().getDirection().toString() + "," + currentNode.getNo().getType().toString();
            str.ref += System.getProperty("line.separator");

            serializeChildren(currentNode.getYes(), str);
            serializeChildren(currentNode.getNo(), str);
        }
    }

    private String deserializeState(String state)
    {
        return _temp;
    }

    private String getStartingState()
    {
        // Generate basic tree string:
        StringBuilder sb = new StringBuilder();
        sb.append("Is the person male?,ROOT,QUESTION");
        sb.append(System.getProperty("line.separator"));
        sb.append("Barack Obama,YES,ANSWER");
        sb.append(System.getProperty("line.separator"));
        sb.append("Queen Elizabeth II,NO,ANSWER");
        return sb.toString();
    }

    private void buildTree(List<Node> nodes, Node currentNode)
    {
        // Assuming format of file is
        // root
        // yes
        // no
        // ie: depth-first traversal

        if(nodes.size() == 0)
        {
            return;
        }

//        if(yesIndex < nodes.size() && noIndex < nodes.size()) {
//            Node yesNode = nodes.get(yesIndex);
//            Node noNode = nodes.get(noIndex);
//            currentNode.setYes(yesNode);
//            currentNode.setNo(noNode);
//        }

        if(currentNode.getType() == NodeType.QUESTION)
        {
            Node yesNode = nodes.remove(0);
            Node noNode = nodes.remove(0);

            // TODO: throw exception if yes and no nodes are null or invalid

            currentNode.setYes(yesNode);
            currentNode.setNo(noNode);
            buildTree(nodes, yesNode);
            buildTree(nodes, noNode);
        }
    }

    public void SetStateTemp(String temp)
    {
        _temp = temp;
    }

    public void SaveState(Context ctx)
    {
        if(FileManager.isExternalStorageWritable())
        {
            String stateStr = serializeState();
            //stateStr = state.g().replaceAll(System.getProperty("line.separator") + " $", "");
            FileManager.writeToFile(ctx, "GoFish", "state.txt", stateStr);
        }
    }

    public void LoadState(Context ctx)
    {
        if(FileManager.isExternalStorageReadable())
        {

            String state = null;
            try {
                state = FileManager.readFile(ctx, "GoFish", "state.txt");
            } catch (FileNotFoundException e) {
                // Game file not found - maybe first run?
                state = getStartingState();
            }

            String[] nodesString = state.split(System.getProperty("line.separator"));

            List<Node> nodes = new ArrayList();

            for (String node : nodesString)
            {
                String[] nodeParts = node.split(",");

                String text = nodeParts[0];
                Direction dir = Direction.valueOf(nodeParts[1]);
                NodeType type = NodeType.valueOf(nodeParts[2]);

                nodes.add(new Node(text, dir, type));
            }

            rootNode =  nodes.get(0);
            currentNode = rootNode;
            buildTree(nodes.subList(1, nodes.size()), currentNode);
        }
    }

    public Node getCurrentNode()
    {
        return currentNode;
    }

    public Node getRoot()
    {
        return rootNode;
    }

    public List<Direction> getStepsHistory()
    {
        return _stepsHistory;
    }

    // This method is used if the user answers "yes"
    public void goYes()
    {
        currentNode = currentNode.getYes();
        _stepsHistory.add(Direction.YES);
    }

    // This method is used if the user answers "no"
    public void goNo()
    {
        currentNode = currentNode.getNo();
        _stepsHistory.add(Direction.NO);
    }

    public Node getYes()
    {
        return currentNode.getYes();
    }

    public Node getNo()
    {
        return currentNode.getNo();
    }

    public boolean atLeaf()
    {
        return currentNode.isLeaf();
    }

    public boolean nextIsLeaf()
    {
        return currentNode.getYes().isLeaf();
    }

    public void resetCurrentNode()
    {
        // Set current node to root
    }

    public void insertNewLeafNode(String newQuestion, String newAnswer)
    {
        // If program guessed wrong we must insert guessed node as new node and a new question

        currentNode.setNo(new Node(currentNode.getText(), Direction.NO, NodeType.ANSWER));
        currentNode.setText(newQuestion);
        currentNode.setType(NodeType.QUESTION);
        currentNode.setYes(new Node(newAnswer, Direction.YES, NodeType.ANSWER));
    }

    public void restoreTree(Node root, List<Direction> stepsHistory)
    {
        rootNode = root;
        _stepsHistory = stepsHistory;
    }

    public enum Direction {
        ROOT, YES, NO
    }

    public enum NodeType {
        QUESTION, ANSWER
    }
}
