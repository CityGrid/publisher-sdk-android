package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.util.Arrays;

public class CGPlacesDetailCategory implements Serializable {
    private int categoryId;
    private String name;
    private int parentId;
    private String parent;
    private CGPlacesDetailGroup[] groups;

    public CGPlacesDetailCategory(int categoryId, String name, int parentId, String parent, CGPlacesDetailGroup[] groups) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
        this.groups = groups;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public String getParent() {
        return parent;
    }

    public CGPlacesDetailGroup[] getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailCategory)) return false;

        CGPlacesDetailCategory category = (CGPlacesDetailCategory) o;

        if (categoryId != category.categoryId) return false;
        if (parentId != category.parentId) return false;
        if (!Arrays.equals(groups, category.groups)) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (parent != null ? !parent.equals(category.parent) : category.parent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + parentId;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (groups != null ? Arrays.hashCode(groups) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("categoryId=").append(categoryId);
        sb.append(",name=").append(name);
        sb.append(",parentId=").append(parentId);
        sb.append(",parent=").append(parent);
        sb.append(",groups=").append(groups == null ? "null" : Arrays.toString(groups));
        sb.append('>');
        return sb.toString();
    }
}
