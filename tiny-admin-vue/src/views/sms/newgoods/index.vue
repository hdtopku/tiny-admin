<template>
  <PageStructure>
    <template #content="{ dataAndFunc }">
      <PcDataList class="hidden sm:block" v-bind="{...dataAndFunc, changeRecordStatus}" @open-edit-modal="openEditModal"/>
      <MobileDataList class="block sm:hidden" v-bind="{...dataAndFunc, changeRecordStatus}"  @open-edit-modal="openEditModal"/>
      <AssignProductModal ref="assignProductModalRef" v-bind="{...dataAndFunc, changeRecordStatus}"  @open-edit-modal="openEditModal"/>
      <EditNewGoodsModal ref="editModalRef" v-bind="dataAndFunc"/>
    </template>
  </PageStructure>
</template>

<script lang="ts" setup>
import { ref, provide } from 'vue';
import AssignProductModal from './AssignProductModal.vue';
import PcDataList from './PcDataList.vue';
import MobileDataList from './MobileDataList.vue';
import {deleteById, execQuery, getAllNewGoodsIds, assignGoods, updateRecord} from '@/api/sms/newGoods.ts';
import EditNewGoodsModal from "@/views/sms/newgoods/EditNewGoodsModal.vue";
import {message} from "ant-design-vue";

// Modal reference to open or close it
const assignProductModalRef = ref<InstanceType<typeof AssignProductModal>>(), editModalRef = ref()

const openAssignProductModal = (record: any) => {
  assignProductModalRef.value?.openModal(record, getAllNewGoodsIds);  // Use optional chaining to handle undefined modalRef
}

const openEditModal = (record: any) => {
  editModalRef.value.openModal(record, updateRecord)
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

// First, provide API functions to the parent component, PageStructure. Then, PageStructure passes dataAndFunc to its child components (PcDataList, MobileDataList, DataModal, etc.). This allows the child components to use the provided API functions for server interactions.
provide('init', { deleteById, saveOrUpdate: assignGoods, execQuery, openModal: openAssignProductModal });
</script>

<style scoped>
/* Optional: Add scoped styles here */
</style>
