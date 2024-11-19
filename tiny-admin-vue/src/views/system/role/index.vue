<template>
  <PageStructure>
    <template #content="{dataAndFunc}">
      <PcDataList class="hidden sm:block" v-bind="dataAndFunc" @open-assign-role-modal="openAssignRoleModal"/>
      <MobileDataList class="block sm:hidden" v-bind="dataAndFunc" @open-assign-role-modal="openAssignRoleModal"/>
      <DataModal ref="modalRef" v-bind="dataAndFunc"/>
      <AssignRoleModal ref="assignRoleModalRef" @query-list="dataAndFunc.queryList"/>
    </template>
  </PageStructure>
</template>

<script lang="ts" setup>
import DataModal from '@/views/system/role/DataModal.vue'
import PcDataList from "@/views/system/role/PcDataList.vue"
import MobileDataList from "@/views/system/role/MobileDataList.vue"
import AssignRoleModal from "@/views/system/role/AssignRoleModal.vue"
import {deleteById, execQuery, saveOrUpdate} from "@/api/system/role.ts";

const modalRef = ref<any>(), assignRoleModalRef = ref<any>()

const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
const openAssignRoleModal = (record: any) => {
  assignRoleModalRef.value.openModal(record)
}
provide('init', {deleteById, saveOrUpdate, execQuery, openModal})
</script>
