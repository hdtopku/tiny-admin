<script lang="ts" setup>
import {Pagination} from "ant-design-vue";
import {EditOutlined} from '@ant-design/icons-vue'
const { dataSource, pagination, loading, queryList, changeRecordStatus, deleteRecordById, openModal } = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
  openModal: Function,
})

const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList({pageNum, pageSize})
}
</script>

<template>
  <div>
    <a-list :data-source="dataSource" :loading="loading"
            :pagination="{...pagination, onChange: handlePageChange}"
            item-layout="horizontal">
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-card class="w-full">
            <template #cover>
              <div class="bg-gray-100 dark:bg-gray-800 h-46">
                <img :src="record.picUrl" alt=""/>
              </div>
            </template>
            <a-card-meta :title="record.bannerName">
              <template #description>
                {{ record.remark }}
                <div class="mt-2">
                  <a-tag>Platform</a-tag>
                  {{ record.platform === 1 ? 'PC' : 'Mobile' }}
                  <a-divider type="vertical"/>
                  <a-tag>Sort Order</a-tag>
                  {{ record.sort }}
                </div>
              </template>
            </a-card-meta>
            <template #actions>
              <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)"/>

              <DeleteRecordConfirm show-icon :record-id="record.id" :record-name="record.bannerName"
                                   @confirm="deleteRecordById?.(record.id)"/>
              <a-button type="link" @click="openModal(record)">
                <EditOutlined/>
              </a-button>
            </template>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>
