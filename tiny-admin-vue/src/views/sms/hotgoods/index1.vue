<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" placeholder="Search product name, remarks"
            @open-modal="openModal" @query-list="queryList"/>
    <PcDataList :data-source="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                @query-list="queryList" @change-record-status="changeRecordStatus"
                @delete-record-by-id="deleteRecordById"
                @open-modal="openModal" @open-edit-modal="openEditModal"/>
    <MobileDataList :data-source="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                    @query-list="queryList" @change-record-status="changeRecordStatus"
                    @delete-record-by-id="deleteRecordById"
                    @open-modal="openModal" @open-edit-modal="openEditModal"/>
    <AssignProductModal ref="modalRef" @query-list="queryList"/>
    <EditNewGoodsModal ref="editModalRef" @query-list="queryList"/>
  </div>
</template>

<script lang="ts" setup>
import AssignProductModal from '@/views/sms/newgoods/AssignProductModal.vue'
import {getAllHotGoodsIds, assignGoods,} from '@/api/sms/hotGoods.ts'
import Search from "@/components/page/Search.vue";
import PcDataList from "@/views/sms/newgoods/PcDataList.vue"
import {message} from "ant-design-vue";
import EditNewGoodsModal from "@/views/sms/newgoods/EditNewGoodsModal.vue";
import MobileDataList from "@/views/sms/newgoods/MobileDataList.vue";

const loading = ref(false), dataSource = ref([]), modalRef = ref(), editModalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const openModal = (record: any) => {
  modalRef.value.openModal(record, getAllHotGoodsIds, assignGoods)
}
const openEditModal = (record: any) => {
  editModalRef.value.openModal(record, updateHotGoods)
}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getSmsHotGoodsPage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
        pagination = {current: res.current, pageSize: res.size, total: res.total}
      })
      .finally(() => {
        loading.value = false
      })
}

const changeRecordStatus = (record: any) => {
  record.loading = true
  updateHotGoods({id: record.id, status: !record.status}).then(() => {
    message.success('Operation successful')
    record.status = !record.status
  }).finally(() => {
    record.loading = false
  })
}

const deleteRecordById = (id: string) => {
  deleteSmsHotGoods(id).then(() => {
    message.success('Deletion successful')
    queryList()
  })
}
</script>
