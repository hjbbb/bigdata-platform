package cn.edu.xidian.bigdataplatform.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-03
 * @description: 清洗任务对象，该对象的数据专门用在前后端之间传递，在前后端共同维护
 */
public class CleansingTaskBean implements Serializable{
    // ------------------------------ 数据库中也有的字段 ------------------------------
    // incremental id，创建后分配，创建前可能为null
    public String id;
    // task name
    public String taskName;
    // app id
    public String appId;
    // start time
    public LocalDateTime startTime;
    // complete time
    public LocalDateTime completeTime;

    // 来自数据源
    public String inputDataSource;
    public String inputDataSourceType;
    public String inputDataSourceTableName;
    // 不由用户分配，根据指定源的表名创建es中的同名索引中
    public String outputTableId;
    // 由用户填写
    public String publisher;

    // 配置文件的位置
    public String configLocation;
    // 用 nohup 启动后，日志文件的位置
    public String logLocation;

    // 1 - 正在运行 2- 停止
    public String status;
    // ------------------------------ 数据库中也有的字段 ------------------------------

    // ------------------------------ 默认的清洗配置 ---------------------------------
    public boolean filterIfNull;     // 过滤空值
    public boolean trimIfString;     // 去除字符串左右的无效字符包括"\n""\r"" "
    public boolean deduplicateByPk;  // 根据主键去重

    // ------------------------------ 高级清洗的字段信息和清洗规则 ---------------------
    // 数据源对应的表的字段信息
    public List<SingleColumn> columnList;
    // 清洗规则
    public List<CleansingRule> rules;
    // ------------------------------ 高级清洗的字段信息和清洗规则 ---------------------

    // 其他信息，会展示在表格中
    public String inputTableName;
    public String duration;


    // -------------- 直接编写配置文件 start ---------------
    // 数据源配置
    public String sourceConfig;
    // 转换配置
    public String transformConfig;
    // 数据结果配置
    public String sinkConfig;
    // -------------- 直接编写配置文件 end ---------------

    public CleansingTaskBean() {
    }

    // MySQL
    // 数值类型
    // TINYINT, SMALLINT, MEDIUMINT, INT, BIGINT, FLOAT, DOUBLE, DECIMAL
    // 支持的操作符
    public static class SingleColumn implements Serializable {
        public String columnName;
        public String databaseSpecificType;
        // 可用的操作符
        public List<Op> availableOps;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"columnName\":\"")
                    .append(columnName).append('\"');
            sb.append(",\"databaseSpecificType\":\"")
                    .append(databaseSpecificType).append('\"');
            sb.append(",\"availableOps\":")
                    .append(availableOps);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class FieldCleansing implements Serializable {
        public String selectedOption;
        public String savedOption;
        public List<Op> options;

        public FieldCleansing() {
        }

        public FieldCleansing(String selectedOption, String savedOption, List<Op> options) {
            this.selectedOption = selectedOption;
            this.savedOption = savedOption;
            this.options = options;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"selectedOption\":\"")
                    .append(selectedOption).append('\"');
            sb.append(",\"savedOption\":\"")
                    .append(savedOption).append('\"');
            sb.append(",\"options\":")
                    .append(options);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class CleansingRule implements Serializable {
        public String fieldName;
        public String fieldOriginalType;
        // 根据某一操作符过滤
        public FieldCleansing fieldCleansing;
        public boolean isEditting;
        public String fieldComment;

        public CleansingRule() {
        }

        public CleansingRule(String fieldName, String fieldOriginalType, FieldCleansing fieldCleansing, boolean isEditting, String fieldComment) {
            this.fieldName = fieldName;
            this.fieldOriginalType = fieldOriginalType;
            this.fieldCleansing = fieldCleansing;
            this.isEditting = isEditting;
            this.fieldComment = fieldComment;
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Op {
        OP_NONE("do-nothing", "不做任何操作"),
        OP_TRIM("trim", "去除空格"), OP_UPPER("upper", "转大写"),
        OP_GT(">", "大于"), OP_GTE(">=", "大于等于"),
        OP_LT("<", "小于"), OP_LTE("<=", "小于等于"),
        OP_EQ("==", "等于"), OP_NE("<>", "不等于");

        private String option;
        private String label;

        Op(String functionName, String labelName) {
            this.option = functionName;
            this.label = labelName;
        }

        public static Op getItemByOption(String option) {
            for (Op value : Op.values()) {
                if (value.option.equals(option)) {
                    return value;
                }
            }
            return OP_NONE;
        }

        @JsonCreator
        public static Op getItem(String json) {
            for (Op value : Op.values()) {
                if (value.option.equals(json)) {
                    return value;
                }
            }
            return OP_NONE;
        }

        public String getOption() {
            return option;
        }

        public String getLabel() {
            return label;
        }

        public static EnumSet<Op> numericOps = EnumSet.of(OP_GT, OP_GTE, OP_LT, OP_LTE, OP_EQ, OP_NE);
        public static EnumSet<Op> stringOps = EnumSet.of(OP_TRIM, OP_UPPER);
    }
}
