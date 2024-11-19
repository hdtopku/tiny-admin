<script setup lang="ts">
import {DownOutlined} from "@ant-design/icons-vue";
import {Pagination} from "ant-design-vue";

const {dataSource, pagination, loading, queryList, changeRecordStatus, deleteRecordById, openModal} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
  openModal: Function,
})

const emit = defineEmits(['openModal', 'queryList'])
const handleTableChange = (pageNum: number, pageSize: number) => {
  queryList({pageNum, pageSize})
}

const columns: any = [
  {
    title: 'Index',
    dataIndex: 'index',
    key: 'index',
    width: 80,
    align: 'center',
  },
  {
    title: 'Image',
    dataIndex: 'picUrl',
    key: 'picUrl',
    width: 100,
    align: 'center',
  },
  {
    title: 'Carousel Name',
    dataIndex: 'bannerName',
    key: 'bannerName',
    width: 200,
    align: 'center',
  },
  {
    title: 'Platform',
    dataIndex: 'platform',
    key: 'platform',
    width: 100,
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
    title: 'Sort',
    dataIndex: 'sort',
    key: 'sort',
    width: 100,
    align: 'center',
  },
  {
    title: 'Actions',
    key: 'actions',
    width: 200,
    fixed: 'right',
  },
]
</script>

<template>
  <a-table
      :columns="columns"
      :dataSource="dataSource"
      :pagination="pagination"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      @change="handleTableChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-else-if="column.dataIndex === 'picUrl'">
        <img :src="record.picUrl" alt=""/>
      </template>
      <template v-else-if="column.dataIndex === 'bannerName'">
        <ToolTip :content="record.bannerName"/>
      </template>
      <template v-else-if="column.dataIndex === 'platform'">
        {{ record.platform === 1 ? 'PC' : 'Mobile' }}
      </template>
      <template v-else-if="column.dataIndex === 'remark'">
        <ToolTip :content="record.remark"/>
      </template>
      <template v-else-if="column.dataIndex === 'sort'">
        {{ record.sort }}
      </template>

      <template v-else-if="column.key === 'actions'">
        <div class="grid grid-cols-2 items-center justify-center">
          <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)"/>

          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link"
            >{{ 'Actions' }}
              <DownOutlined/>
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="openModal(record)">{{ 'Edit' }}</a-button>
                </a-menu-item>
                <a-menu-item>
                  <DeleteRecordConfirm :record-id="record.id" :record-name="record.bannerName"
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
