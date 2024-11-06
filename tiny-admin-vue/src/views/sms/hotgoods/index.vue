<template>
  <PageStructure>
    <template #pc="{dataSource, loading, pagination, queryList, changeRecordStatus, deleteRecordById}">
      <PcDataList :data-source="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                  @change-record-status="changeRecordStatus" @query-list="queryList"
                  @delete-record-by-id="deleteRecordById" @open-edit-modal="openEditModal"/>
    </template>
    <template #mobile="{dataSource, loading, pagination, queryList, changeRecordStatus, deleteRecordById}">
      <MobileDataList :data-source="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                      @change-record-status="changeRecordStatus" @query-list="queryList"
                      @delete-record-by-id="deleteRecordById"/>
    </template>
    <template #modal="{queryList}">
      <DataModal ref="modalRef" @query-list="queryList"/>
    </template>
    <EditNewGoodsModal ref="editModalRef"/>
  </PageStructure>
</template>
<script lang="ts" setup>
import DataModal from '@/views/sms/newgoods/DataModal.vue'
import {
  deleteSmsHotGoods,
  getAllHotGoodsIds,
  getSmsHotGoodsPage,
  saveHotGoods,
  updateHotGoods
} from '@/api/sms/hotGoods.ts'
import PcDataList from "@/views/sms/newgoods/PcDataList.vue"
import EditNewGoodsModal from "@/views/sms/newgoods/EditNewGoodsModal.vue"
import MobileDataList from "@/views/sms/newgoods/MobileDataList.vue"

const editModalRef = ref<any>(), modalRef = ref<any>()
const openModal = (record: any) => {
  modalRef.value.openModal(record, getAllHotGoodsIds, saveHotGoods)
}
provide('init', {
  deleteFunc: deleteSmsHotGoods,
  saveOrUpdateFunc: updateHotGoods,
  pageFunc: getSmsHotGoodsPage,
  openModal
})
const openEditModal = (record: any) => {
  editModalRef.value.openModal(record, updateHotGoods)
}
</script>
