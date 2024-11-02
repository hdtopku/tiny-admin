<template>
  <div>
    <Search :loading="loading" :search-form="searchParams" :top="84" placeholder="搜索商品名称、备注"
            @open-modal="openModal" @query-list="queryList"/>
    <PcNewGoodsList :data-source="dataSource" :loading="loading" :pagination="pagination"
                    @query-list="queryList" @open-modal="openModal"/>
    <NewGoodsModal ref="modalRef"></NewGoodsModal>
  </div>
</template>
<script lang="ts" setup>
import NewGoodsModal from '@/views/sms/newgoods/NewGoodsModal.vue'
import {getSmsNewGoodsPage} from '@/api/sms/newGoods.ts'
import PcNewGoodsList from "@/views/sms/newgoods/PcNewGoodsList.vue";
import Search from "@/components/Search.vue";

const openModal = (record: any) => {
  modalRef.value.openModal(record)
}
const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

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
</script>
