<template>
  <PageStructure>
    <template #content="{ dataAndFunc }">
      <PcDataList class="hidden sm:block" v-bind="dataAndFunc" @openPreview="openPreview" />
      <MobileDataList class="block sm:hidden" v-bind="dataAndFunc" @openPreview="openPreview" />
      <DataModal ref="modalRef" v-bind="dataAndFunc" />
      <ProductPreview ref="previewRef"/>
    </template>
  </PageStructure>
</template>

<script lang="ts" setup>
import { ref, provide } from 'vue';
import DataModal from './DataModal.vue';
import PcDataList from './PcDataList.vue';
import MobileDataList from './MobileDataList.vue';
import { deleteById, execQuery, saveOrUpdate } from '@/api/pms/product.ts';
import ProductPreview from "@/views/pms/product/ProductPreview.vue";

// Modal reference to open or close it
const modalRef = ref<InstanceType<typeof DataModal>>();

const openModal = (record: any) => {
  modalRef.value?.openModal(record);  // Use optional chaining to handle undefined modalRef
};
const previewRef = ref<InstanceType<typeof ProductPreview>>();
const openPreview = (record: any) => {
  previewRef.value?.openModal(record);  // Use optional chaining to handle undefined previewRef
}

// First, provide API functions to the parent component, PageStructure. Then, PageStructure passes dataAndFunc to its child components (PcDataList, MobileDataList, DataModal, etc.). This allows the child components to use the provided API functions for server interactions.
provide('init', { deleteById, saveOrUpdate, execQuery, openModal });
</script>

<style scoped>
/* Optional: Add scoped styles here */
</style>
