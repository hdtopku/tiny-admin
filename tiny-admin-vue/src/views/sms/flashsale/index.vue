<template>
  <PageStructure>
    <template #content="{dataAndFunc}">
      <PcDataList class="hidden sm:block" v-bind="dataAndFunc" @open-modal="openModal"
                  @open-assign-goods-modal="openAssignGoodsModal"/>
      <MobileDataList class="block sm:hidden" v-bind="dataAndFunc" @open-assign-goods-modal="openAssignGoodsModal"
                      @open-modal="openModal"/>
      <DataModal ref="modalRef" v-bind="dataAndFunc"/>
      <AssignGoodsModal ref="assignGoodsModal" v-bind="dataAndFunc"/>
    </template>
  </PageStructure>
</template>
<script lang="ts" setup>
import DataModal from '@/views/sms/flashsale/DataModal.vue'
import AssignGoodsModal from "@/views/sms/flashsale/AssignGoodsModal.vue";
import PcDataList from "./PcDataList.vue"
import MobileDataList from "./MobileDataList.vue"
import {deleteById, execQuery, saveOrUpdate} from "@/api/sms/flashSale.ts"
import {assignGoods, getFlashGoodsIds} from "@/api/sms/flashGoodsRel.ts";

const modalRef = ref<any>(), assignGoodsModal = ref<any>();
const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
const openAssignGoodsModal = (record: any) => {
  assignGoodsModal.value.openModal(record, getFlashGoodsIds, assignGoods)
}

provide('init', {deleteById, saveOrUpdate, execQuery, openModal})

</script>
