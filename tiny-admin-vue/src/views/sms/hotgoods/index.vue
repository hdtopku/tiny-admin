<template>
  <PageStructure>
    <template #list="dataAndFunc">
      <PcDataList class="hidden sm:block" v-bind="dataAndFunc" @open-modal="openModal"/>
      <MobileDataList class="block sm:hidden" v-bind="dataAndFunc" @open-edit-modal="openEditModal"
                      @open-modal="openModal"/>
      <DataModal ref="modalRef" @query-list="dataAndFunc.queryList"/>
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
  queryFunc: getSmsHotGoodsPage,
  openModal
})
const openEditModal = (record: any) => {
  editModalRef.value.openModal(record, updateHotGoods)
}
</script>
