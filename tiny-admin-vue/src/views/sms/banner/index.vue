<template>
  <div>
    <a-affix :offset-top="84">
      <Search placeholder="搜索轮播名称、备注" :loading="loading" @open-modal="openModal" @query-list="queryList"/>
    </a-affix>
    <PcBannerList :loading="loading" :dataSource="dataSource" :pagination="pagination" @open-modal="openModal"
                  @query-list="queryList"/>
    <BannerModal @query-list="queryList" ref="modalRef"></BannerModal>
  </div>
</template>
<script lang="ts" setup>

import BannerModal from '@/views/sms/banner/BannerModal.vue'
import {getBannerPage} from '@/api/sms/banner.ts'
import Search from "@/components/Search.vue";
import PcBannerList from "@/views/sms/banner/PcBannerList.vue";

const openModal = (record: any) => {
  modalRef.value.openModal(record)
}

const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {keyword: '', status: true, pageNum: 1, pageSize: 10}

const queryList = (params = {}) => {
  loading.value = true
  searchParams = {...searchParams, ...params}
  getBannerPage(searchParams)
      .then((res: any) => {
        dataSource.value = res.records
      })
      .finally(() => {
        loading.value = false
      })
}
</script>
