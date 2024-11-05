<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" placeholder="搜索商品名称、备注"
            @open-modal="openModal" @query-list="queryList"/>
    <PcDataList :data-source="dataSource" :loading="loading" :pagination="pagination" class="hidden sm:block"
                @query-list="queryList" @change-record-status="changeRecordStatus"
                @delete-record-by-id="deleteRecordById"
                @open-modal="openModal" @open-edit-modal="openEditModal"/>
    <MobileDataList :data-source="dataSource" :loading="loading" :pagination="pagination" class="block sm:hidden"
                    @query-list="queryList" @change-record-status="changeRecordStatus"
                    @delete-record-by-id="deleteRecordById"
                    @open-modal="openModal" @open-edit-modal="openEditModal"/>
    <DataModal ref="modalRef" @query-list="queryList"></DataModal>
    <EditNewGoodsModal ref="editModalRef" @query-list="queryList"></EditNewGoodsModal>
  </div>
</template>
<script lang="ts" setup>
import DataModal from '@/views/sms/newgoods/DataModal.vue'
import {deleteSmsNewGoods, getSmsNewGoodsPage, saveNewGoods, updateNewGoods} from '@/api/sms/newGoods.ts'
import PcDataList from "@/views/sms/newgoods/PcDataList.vue"
import Search from "@/components/Search.vue"
import MobileDataList from "@/views/sms/newgoods/MobileDataList.vue"
import {message} from "ant-design-vue"
import {t} from "@/utils/i18n.ts"
import EditNewGoodsModal from "@/views/sms/newgoods/EditNewGoodsModal.vue"

const loading = ref(false), dataSource = ref([]), modalRef = ref(), editModalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const openModal = (record: any) => {
  modalRef.value.openModal(record, saveNewGoods)
}
const openEditModal = (record: any) => {
  editModalRef.value.openModal(record)
}
const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getSmsNewGoodsPage(searchParams)
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
  updateNewGoods({id: record.id, status: !record.status}).then(() => {
    message.success(t('操作成功'))
    record.status = !record.status
  }).finally(() => {
    record.loading = false
  })
}

const deleteRecordById = (id: string) => {
  deleteSmsNewGoods(id).then(() => {
    message.success(t('删除成功'))
    queryList()
  })
}
</script>
