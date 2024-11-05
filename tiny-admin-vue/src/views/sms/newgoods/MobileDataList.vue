<script lang="ts" setup>
import {Pagination} from "ant-design-vue";
import {EditOutlined} from '@ant-design/icons-vue'
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue";
import SwitchStatusConfirm from "@/components/SwitchStatusConfirm.vue";

const {dataSource, pagination, isLoading} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  isLoading: Boolean
})
const emit = defineEmits(['openModal', 'queryList', 'changeRecordStatus', 'deleteRecordById', 'openEditModal'])

const handlePageChange = (current: number, pageSize: number) => {
  emit('queryList', {pageNum: current, pageSize})
}
</script>

<template>
  <div>
    <a-list :data-source="dataSource" :loading="isLoading"
            :pagination="{...pagination, onChange: handlePageChange}" item-layout="horizontal">
      <template #renderItem="{ item:record }">
        <a-list-item>
          <a-card class="w-full">
            <template #cover>
              <div class="bg-gray-100 dark:bg-gray-800 h-46">
                <ImageCarousel :img-urls="record?.albumPics?.split(',')" class="mx-auto"/>
              </div>
            </template>
            <a-card-meta :title="record.goodsName">
              <template #description>
                {{ record.remark }}
                <div class="mt-2">
                  <a-tag>排序</a-tag>
                  {{ record.sort }}
                </div>
              </template>
            </a-card-meta>
            <template #actions>
              <SwitchStatusConfirm :record="record" @change-record-status="emit('changeRecordStatus', record)"/>
              <DeleteRecordConfirm :record-id="record.id" :record-name="record.goodsName" show-icon
                                   @delete-record-by-id="emit('deleteRecordById', record.id)"/>
              <a-button type="link" @click="() => emit('openEditModal', record)">
                <EditOutlined/>
              </a-button>
            </template>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>