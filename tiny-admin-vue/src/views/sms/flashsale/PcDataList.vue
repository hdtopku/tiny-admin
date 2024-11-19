<script lang="ts" setup>
import { DownOutlined } from "@ant-design/icons-vue";
import { Pagination, PaginationProps } from "ant-design-vue";
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue";

const {
  dataSource,
  pagination,
  loading,
  queryList,
  changeRecordStatus,
  deleteRecordById,
} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
});
const emit = defineEmits(["openModal", "openAssignGoodsModal"]);
const handleTableChange = (pagination: PaginationProps) => {
  queryList?.({
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  });
};
const columns: any = [
  {
    title: "Index",
    dataIndex: "index",
    key: "index",
    align: "center",
    width: 60,
  },
  {
    title: "Event Name",
    dataIndex: "activityName",
    key: "activityName",
    align: "center",
    width: 120,
  },
  {
    title: "Remarks",
    dataIndex: "remark",
    key: "remark",
    width: 250,
  },
  {
    title: "Start Time",
    dataIndex: "startTime",
    key: "startTime",
    width: 120,
    align: "center",
  },
  {
    title: "End Time",
    dataIndex: "endTime",
    key: "endTime",
    width: 120,
    align: "center",
  },
  {
    title: "Creation Time",
    dataIndex: "createTime",
    key: "createTime",
    width: 120,
    align: "center",
  },
  {
    title: "Update Time",
    dataIndex: "updateTime",
    key: "updateTime",
    width: 120,
    align: "center",
  },
  {
    title: "Actions",
    key: "operation",
    width: 200,
    align: "center",
    fixed: "right",
  },
];
</script>

<template>
  <a-table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      @change="handleTableChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-if="column.key === 'activityName'">
        <ToolTip :content="record.activityName" :length="30" />
        <a-tag>
          Event includes <b class="text-red-500">{{ record?.goodsIds?.length }}</b> items
        </a-tag>
      </template>
      <template v-if="column.key === 'remark'">
        <ToolTip :content="record.remark" :length="50" />
      </template>
      <template v-if="column.key === 'operation'">
        <div class="grid grid-cols-2 items-center justify-center">
          <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)" />
          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link">
              Actions
              <DownOutlined />
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="emit('openModal', record)">Edit</a-button>
                </a-menu-item>
                <a-menu-item>
                  <DeleteRecordConfirm
                      :record-id="record.id"
                      :record-name="record.activityName"
                      @confirm="deleteRecordById?.(record.id)"
                  />
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
          <div>
            <a-button class="text-orange-300" type="link" @click="emit('openAssignGoodsModal', record)">
              Assign Goods
            </a-button>
          </div>
        </div>
      </template>
    </template>
  </a-table>
</template>
