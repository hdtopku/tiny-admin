<template>
  <PageStructure>
    <template #content="{ dataAndFunc }">
      <PcDataList class="hidden sm:block" v-bind="dataAndFunc" />
      <MobileDataList class="block sm:hidden" v-bind="dataAndFunc" />
      <DataModal ref="modalRef" v-bind="dataAndFunc" />
    </template>
  </PageStructure>
</template>

<script lang="ts" setup>
import { ref, provide } from 'vue';
import DataModal from './DataModal.vue';
import PcDataList from './PcDataList.vue';
import MobileDataList from './MobileDataList.vue';
import { deleteById, execQuery, saveOrUpdate } from '@/api/sms/brand.ts';

// Modal reference to open or close it
const modalRef = ref<InstanceType<typeof DataModal>>();

const openModal = (record: any) => {
  modalRef.value?.openModal(record);  // Use optional chaining to handle undefined modalRef
};

// First, provide API functions to the parent component, PageStructure. Then, PageStructure passes dataAndFunc to its child components (PcDataList, MobileDataList, DataModal, etc.). This allows the child components to use the provided API functions for server interactions.
provide('init', { deleteById, saveOrUpdate, execQuery, openModal });
</script>

<style scoped>
/* Optional: Add scoped styles here */
</style>
