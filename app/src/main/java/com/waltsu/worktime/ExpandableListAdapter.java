package com.waltsu.worktime;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * ExpandableList for listing tasks.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    /**
     * Context
     */
    private Context context;

    /**
     * List's headers.
     */
    private List<String> headers;

    /**
     * List's data.
     */
    private HashMap<String, List<String>> childs;

    /**
     * Defines values.
     *
     * @param context context
     * @param headers list's headers
     * @param childs list's data
     */
    public ExpandableListAdapter(Context context, List<String> headers,
                                 HashMap<String, List<String>> childs) {
        this.context = context;
        this.childs = childs;
        this.headers = headers;
    }

    /**
     * Returns schild at position.
     *
     * @param groupPosition group position
     * @param childPosititon child position
     * @return child at given position
     */
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childs.get(this.headers.get(groupPosition))
                .get(childPosititon);
    }

    /**
     * Returns childs id
     * @param groupPosition group position
     * @param childPosition child position
     * @return child's id at given position
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Returns childs view
     * @param groupPosition group position
     * @param childPosition child position
     * @param isLastChild if last child
     * @param convertView View
     * @param parent Parent view
     * @return child's view
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView,
                             ViewGroup parent) {

        final String childText
                = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.taskinfo, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.taskinfo);

        txtListChild.setText(childText);
        return convertView;
    }

    /**
     * Returns child count
     *
     * @param groupPosition group position
     * @return Child count in group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childs.get(this.headers.get(groupPosition))
                .size();
    }

    /**
     * Returns group of given group position.
     *
     * @param groupPosition group position
     * @return group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }

    /**
     * Returns group count
     *
     * @return group count
     */
    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    /**
     * Returns group's id
     *
     * @param groupPosition group position
     * @return group's id
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns group's view
     *
     * @param groupPosition group position
     * @param isExpanded is exanded or not
     * @param convertView view
     * @param parent Parent view
     * @return group's view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.taskitem, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.taskitem);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    /**
     * Returns if has stable ids
     * @return stable ids
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Returns if child is selectable.
     *
     * @param groupPosition group position
     * @param childPosition child position
     * @return if selectable
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}