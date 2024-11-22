<script lang="ts" setup>
import {DownOutlined} from "@ant-design/icons-vue"
import {message, Pagination} from "ant-design-vue"
import ToolTip from "@/components/ToolTip.vue";
import ImageCarousel from "@/views/pms/product/ImageCarousel.vue";
import DeleteRecordConfirm from "@/components/page/DeleteRecordConfirm.vue";
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue";

const {dataSource, pagination, loading, queryList, deleteRecordById, openModal, changeRecordStatus} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  deleteRecordById: Function,
  openModal: Function,
  changeRecordStatus: Function,
})

const emit = defineEmits(['openEditModal'])

const handlePageChange = (current: number, pageSize: number) => {
  queryList?.({pageNum: current, pageSize})
}

const columns: any = [
  {
    title: 'Index',
    dataIndex: 'index',
    key: 'index',
    width: 60,
    align: 'center',
  },
  {
    title: 'Product Image',
    dataIndex: 'album',
    key: 'album',
    width: 100,
    align: 'center',
  },
  {
    title: 'Product Name',
    dataIndex: 'productName',
    key: 'productName',
    width: 200,
    align: 'center',
  },
  {
    title: 'Remark',
    dataIndex: 'remark',
    key: 'remark',
    width: 250,
    align: 'center',
  },
  {
    title: 'Market Price',
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
    align: 'center',
  },
  {
    title: 'Sale Price',
    dataIndex: 'salePrice',
    key: 'salePrice',
    width: 100,
    align: 'center',
  },
  {
    title: 'Sort Order',
    dataIndex: 'sort',
    key: 'sort',
    width: 100,
    align: 'center',
  },
  {
    title: 'Action',
    key: 'operation',
    width: 180,
    fixed: 'right',
  },
]
</script>

<template>
  <a-table :columns="columns" :dataSource="dataSource" :loading="loading" :pagination="pagination"
           :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }" @change="handlePageChange">
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-else-if="column.dataIndex === 'album'">
        <ImageCarousel :img-urls="record?.album?.split(',')" :width="'120px'"/>
      </template>
      <template v-else-if="column.dataIndex === 'productName'">
        <ToolTip :content="record.productName" :length="30"/>
      </template>
      <template v-else-if="column.dataIndex === 'remark'">
        <ToolTip :content="record.remark" :length="50"/>
      </template>

      <template v-else-if="column.key === 'operation'">
        <div class="grid grid-cols-2 items-center justify-center">
          <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)"/>

          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link"
            >Action
              <DownOutlined/>
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="emit('openEditModal', record)">Edit</a-button>
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
