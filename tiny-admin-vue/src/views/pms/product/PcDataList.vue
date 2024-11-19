<script lang="ts" setup>
import { DownOutlined } from "@ant-design/icons-vue";
import ImageCarousel from "@/views/pms/product/ImageCarousel.vue";
import {  Pagination } from "ant-design-vue";

const {dataSource, pagination, loading, queryList, changeRecordStatus, deleteRecordById, openModal} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
  openModal: Function,
})

const handlePageChange = (current: number, pageSize: number) => {
  queryList?.({pageNum: current, pageSize})
};
const columns: any = [
  {
    title: "No.",
    key: "index",
    width: 60,
  },
  {
    title: "Product Pics",
    dataIndex: "album",
    key: "album",
    width: 100,
  },
  {
    title: "Product Name",
    dataIndex: "productName",
    key: "productName",
    width: 150,
  },
  {
    title: "Product Desc",
    dataIndex: "productDesc",
    key: "productDesc",
    width: 250,
  },
  {
    title: "Market Price",
    dataIndex: "marketPrice",
    key: "marketPrice",
    width: 100,
  },
  {
    title: "Sale Price",
    dataIndex: "salePrice",
    key: "salePrice",
    width: 100,
  },
  {
    title: "Stock",
    dataIndex: "stock",
    key: "stock",
    width: 100,
  },
  {
    title: "Action",
    key: "Action",
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
      @change="handlePageChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-else-if="column.dataIndex === 'album'">
        <ImageCarousel :img-urls="record?.album || []" />
      </template>
      <template v-else-if="column.dataIndex === 'productName'">
        <ToolTip :content="record.productName" :length="50" />
      </template>
      <template v-else-if="column.dataIndex === 'productDesc'">
        <ToolTip :content="record.productDesc" :length="100" />
      </template>
      <template v-else-if="column.key === 'Action'">
        <div class="grid grid-cols-2 items-center justify-center">
          <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)"/>
          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link">
              Actions
              <DownOutlined />
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="openModal?.(record)">
                    Edit Product
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <DeleteRecordConfirm :record-id="record.id" :record-name="record.productName"
                                       @confirm="deleteRecordById?.(record.id)"/>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </template>
    </template>
  </a-table>
</template>
