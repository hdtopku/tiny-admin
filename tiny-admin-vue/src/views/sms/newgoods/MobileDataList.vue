<script lang="ts" setup>
import {message, Pagination} from "ant-design-vue";
import {EditOutlined} from '@ant-design/icons-vue'
import ImageCarousel from "@/views/pms/product/ImageCarousel.vue";
import SwitchStatusConfirm from "@/components/page/SwitchStatusConfirm.vue";
import {updateRecord} from "@/api/sms/newGoods.ts";
import DeleteRecordConfirm from "@/components/page/DeleteRecordConfirm.vue";

const {dataSource, pagination, loading, queryList, deleteRecordById, openModal} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  deleteRecordById: Function,
  openModal: Function,
})
const emit = defineEmits(['openEditModal'])

const handlePageChange = (current: number, pageSize: number) => {
  queryList?.({pageNum: current, pageSize})
}
const changeRecordStatus = async (record: any) => {
  record.loading = true
  try {
    await updateRecord({id: record.id, enabled: !record.enabled})
    record.enabled = !record.enabled
    message.success('Success')
  } finally {
    record.loading = false
  }
}
</script>

<template>
  <a-list :data-source="dataSource" :loading="isLoading"
          :pagination="{...pagination, onChange: handlePageChange}" item-layout="horizontal">
    <template #renderItem="{ item:record }">
      <a-list-item>
        <a-card class="w-full">
          <template #cover>
            <div class="bg-gray-100 dark:bg-gray-800 h-46">
              <ImageCarousel :img-urls="record?.album?.split(',')" class="mx-auto"/>
            </div>
          </template>
          <a-card-meta :title="record.productName">
            <template #description>
              {{ record.remark }}
              <div class="mt-2">
                <a-tag>排序</a-tag>
                {{ record.sort }}
              </div>
            </template>
          </a-card-meta>
          <template #actions>
            <SwitchStatusConfirm :record="record" @confirm="changeRecordStatus?.(record)"/>

            <DeleteRecordConfirm :record-id="record.id" :record-name="record.productName" show-icon
                                 @confirm="deleteRecordById?.(record.id)"/>
            <a-button class="inline-flex items-center" type="link" @click="() => emit('openEditModal', record)">
              <EditOutlined/>
            </a-button>
          </template>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>