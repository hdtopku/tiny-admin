<template>
  <PageStructure>
    <template #content="{dataAndFunc}">
      <PcDataList class="hidden sm:block" v-bind="dataAndFunc" @open-modal="openModal"/>
      <MobileDataList class="block sm:hidden" v-bind="dataAndFunc" @open-edit-modal="openEditModal"
                      @open-modal="openModal"/>
      <DataModal ref="modalRef" v-bind="dataAndFunc"/>
      <AddGoodsModal ref="addModalRef" v-bind="dataAndFunc"></AddGoodsModal>
    </template>
  </PageStructure>
</template>
<script lang="ts" setup>
import DataModal from '@/views/sms/flashsale/DataModal.vue'
import PcDataList from "./PcDataList.vue"
import MobileDataList from "./MobileDataList.vue"
import {deleteById, execQuery, saveOrUpdate} from "@/api/sms/flashSale.ts"
import AddGoodsModal from "@/views/sms/flashsale/AddGoodsModal.vue";

const modalRef = ref<any>()
const openModal = (record: any) => {
  modalRef.value.openModal(record)
}

provide('init', {deleteById, saveOrUpdate, execQuery, openModal})

</script>
