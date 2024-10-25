<template>
  <div>
    <!--   Search -->
    <a-affix :offset-top="84">
      <Search :loading="loading" :placeholder="$t('搜索用户名、昵称、邮箱、手机号')" @open-modal="openModal"
              @query-list="queryList"></Search>
    </a-affix>
    <div>
      <!--   PC Data List -->
      <PcGoodsList :dataSource="dataSource" :loading="loading" :pagination="pagination" class="sm:block hidden"
                   @open-modal="openModal"
                   @query-list="queryList"></PcGoodsList>
      <!--   Mobile Data List -->
      <MobileGoodsList :dataSource="dataSource" :is-loading="loading" :pagination="pagination" class="block sm:hidden"
                       @load-more="loadMore" @open-modal="openModal"></MobileGoodsList>
    </div>
    <!--  add/edit Data Modal -->
    <GoodsModal ref="modalRef" @query-list="queryList"/>
  </div>
</template>
<script lang="ts" setup>
import {getGoodsPage} from '@/api/pms/goods.ts'
import GoodsModal from '@/views/pms/goods/GoodsModal.vue'
import PcGoodsList from "@/views/pms/goods/PcGoodsList.vue";
import MobileGoodsList from "@/views/pms/goods/MobileGoodsList.vue";
import Search from "@/components/Search.vue";

const loading = ref(false), dataSource = ref([]), modalRef = ref()
let pagination: any = {}, searchParams: any = {}

const queryList = ({keyword = '', status = true, pageNum = 1, pageSize = 2}) => {
  loading.value = true
  searchParams = {...searchParams, keyword, status, pageNum, pageSize}
  getGoodsPage(searchParams).then((res: any) => {
    dataSource.value = res.records
    pagination = {current: res.current, pageSize: res.size, total: res.total,}
  }).finally(() => {
    loading.value = false
  })
}
const loadMore = () => {
  loading.value = true
  searchParams.pageNum = pagination.current + 1
  getGoodsPage(searchParams).then((res: any) => {
    dataSource.value = dataSource.value.concat(res.records)
    pagination.current = res.current
    pagination.total = res.total
  }).finally(() => {
    loading.value = false
  })
}
const openModal = (record: any = {}) => {
  modalRef.value.openModal(record)
}
let isMobile = window.innerWidth < 576
window.onresize = () => {
  if (isMobile && window.innerWidth >= 576 || !isMobile && window.innerWidth < 576) {
    queryList({pageNum: 1})
  }
  isMobile = window.innerWidth < 576
}
</script>