package by.epam_training.homework04.entity;

import java.io.Serializable;
import java.util.Objects;

public class Code implements Serializable {
    private static final long serialVersionUID = -4492545223949089783L;
    private String blocksOfCode;


    public Code() {
    }

    public Code(String blocksOfCode) {
        this.blocksOfCode = blocksOfCode;
    }

    public String getBlocksOfCode() {
        return blocksOfCode;
    }

    public void setBlocksOfCode(String blocksOfCode) {
        this.blocksOfCode = blocksOfCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Objects.equals(blocksOfCode, code.blocksOfCode);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + blocksOfCode.hashCode();
        return result;
    }
}
