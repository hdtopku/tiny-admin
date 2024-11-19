<script lang="ts" setup>
import {Pagination} from "ant-design-vue";
import {EditOutlined} from "@ant-design/icons-vue";

const {dataSource, pagination, loading, queryList, changeRecordStatus, deleteRecordById, openModal} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
  openModal: Function,
})
const handlePageChange = (pageNum: number, pageSize: number) => {
  queryList?.({pageNum, pageSize})
}
</script>

<template>
  <div>
    <a-list :data-source="dataSource" :loading="loading"
            :pagination="{pagination, onChange: handlePageChange}"
            item-layout="horizontal">
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-card class="w-full">
            <template #actions>
              <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)"/>
              <DeleteRecordConfirm show-icon :record-id="record.id" :record-name="record.brandName"
                                   @confirm="deleteRecordById?.(record.id)"/>
              <a-button class="inline-flex items-center" type="link" @click="openModal(record)">
                <EditOutlined/>
              </a-button>
            </template>
            <a-list-item-meta>
              <template #title>

                <a-tooltip :title="record.brandStory" placement="bottomLeft">
                  <a-button size="small" type="link">{{ record.brandName }}</a-button>
                </a-tooltip>
              </template>
              <template #description>
                <a-tag>Remark</a-tag>
                <a-typography-text :content="record.remark" :ellipsis="{ rows: 3, expandable: true, symbol: 'more' }"/>
              </template>
              <template #avatar>
                <a-avatar :src="record.logo" size="large"/>
              </template>
            </a-list-item-meta>
          </a-card>
        </a-list-item>

      </template>
    </a-list>
  </div>
</template>
