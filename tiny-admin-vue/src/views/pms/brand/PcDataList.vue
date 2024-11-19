<script lang="ts" setup>
import { DownOutlined } from "@ant-design/icons-vue";
import { Pagination } from "ant-design-vue";
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue";

const { dataSource, pagination, loading, queryList, changeRecordStatus, deleteRecordById, openModal } = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
  openModal: Function,
});

const handleTableChange = (current: number, pageSize: number) => {
  queryList?.({
    pageNum: current,
    pageSize: pageSize,
  });
};

const columns: any = [
  {
    title: "Index",
    key: "index",
    width: 60,
  },
  {
    title: "Brand Logo",
    dataIndex: "logo",
    key: "logo",
    width: 100,
  },
  {
    title: "Brand Name",
    dataIndex: "brandName",
    key: "brandName",
    width: 150,
  },
  {
    title: "Brand Description",
    dataIndex: "brandDesc",
    key: "brandDesc",
    width: 250,
  },
  {
    title: "Brand Story",
    dataIndex: "brandStory",
    key: "brandStory",
    width: 250,
  },
  {
    title: "Actions",
    key: "operation",
    fixed: "right",
    width: 180,
  },
];
</script>

<template>
  <a-table
      :columns="columns"
      :dataSource="dataSource"
      :pagination="pagination"
      :loading="loading"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      @change="handleTableChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-else-if="column.dataIndex === 'logo'">
        <a-avatar :src="record.logo" shape="square" size="large" />
      </template>
      <template v-else-if="column.dataIndex === 'brandDesc'">
        <ToolTip :content="record.brandDesc" :length="100" />
      </template>
      <template v-else-if="column.dataIndex === 'brandStory'">
        <ToolTip :content="record.brandStory" :length="100" />
      </template>
      <template v-else-if="column.key === 'operation'">
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
                  <a-button type="link" @click="openModal?.(record)">
                    Edit Brand
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <DeleteRecordConfirm
                      :record-id="record.id"
                      :record-name="record.brandName"
                      @confirm="deleteRecordById?.(record.id)"
                  />
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </template>
    </template>
  </a-table>
</template>
