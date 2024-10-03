<script setup lang="ts">
import FlashSaleModal from "@/views/sms/flashsale/FlashSaleModal.vue";
import {getFlashSalePage} from "@/api/sms/flashSale.ts";
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import AddGoodsModal from "@/views/sms/flashsale/AddGoodsModal.vue";
import RemoveGoodsModal from "@/views/sms/flashsale/RemoveGoodsModal.vue";

const loading = ref(false)
const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  status: true,
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
})
const queryList = () => {
  loading.value = true
  getFlashSalePage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
  }).finally(() => {
    loading.value = false
  })
}
queryList()
const dataSource = ref([])
const modalRef = ref()
const handleAdd = () => {
  modalRef.value.showModal()
}
const handleEdit = (record: any) => {
  modalRef.value.showModal(record)
}
const queryByStatus = () => {

}

const addModalRef = ref()
const assignGoods = (record: any) => {
  addModalRef.value.openModal(record)
}
const removeModalRef = ref()
const removeGoods = (record: any) => {
  removeModalRef.value.openModal(record)
}

const columns: any = [{
  title: '序号',
  dataIndex: 'index',
  key: 'index',
  align: 'center',
  width: 60,
}, {
  title: '活动名称',
  dataIndex: 'activityName',
  key: 'activityName',
  align: 'center',
  width: 120,
}, {
  title: '备注',
  dataIndex: 'remark',
  key: 'remark',
  width: 160,
}, {
  title: '开始时间',
  dataIndex: 'startTime',
  key: 'startTime',
  width: 120,
  align: 'center',
}, {
  title: '结束时间',
  dataIndex: 'endTime',
  key: 'endTime',
  width: 120,
  align: 'center',
}, {
  title: '创建时间',
  dataIndex: 'createTime',
  key: 'createTime',
  width: 120,
  align: 'center',
}, {
  title: '修改时间',
  dataIndex: 'updateTime',
  key: 'updateTime',
  width: 120,
  align: 'center',
}, {
  title: '操作',
  key: 'operation',
  width: 160,
  align: 'center',
  fixed: 'right',
}]
</script>

<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <a-button :loading="loading" type="primary" @click="handleAdd">新增</a-button>
          <a-input id="keyword" v-model:value="searchForm.keyword" allow-clear
                   autocomplete="off" class="text-left"
                   placeholder="搜索活动名称、备注"
                   type="text" @keyup.enter.native="queryList">
            <template #prefix>
              <a-switch v-model:checked="searchForm.status" :loading="loading" checked-children="已启用"
                        class="flex-shrink-0" un-checked-children="已禁用" @change="queryByStatus"/>
            </template>
            <template #suffix>
              <a-button type="primary" @click="queryList">搜索</a-button>
            </template>
          </a-input>
        </div>
      </div>
    </div>

    <a-table :columns="columns" :data-source="dataSource" :loading="loading"
             :pagination="pagination" :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }">
      <template #bodyCell="{record, index, column}">
        <template v-if="column.key === 'index'">
          {{ index + 1 }}
        </template>
        <template v-if="column.key === 'activityName'">
          <span v-if="record.activityName?.length <= 20">{{ record.activityName }}</span>
          <a-tooltip v-else :arrow="false">
            <template #title>
              <span>{{ record.activityName }}</span>
            </template>
            <span>{{ record.activityName?.substring(0, 20) }}</span>
            <span v-if="record.activityName?.length > 20">...</span>
          </a-tooltip>
          <a-tag>活动共包含<b class="text-red-500">{{ record.goodsIds.length }}</b>件商品</a-tag>
        </template>
        <template v-if="column.key === 'remark'">
          <span v-if="record.remark?.length <= 20">{{ record.remark }}</span>
          <a-tooltip v-else :arrow="false">
            <template #title>
              <span>{{ record.remark }}</span>
            </template>
            <span>{{ record.remark?.substring(0, 20) }}</span>
            <span v-if="record.remark?.length > 20">...</span>
          </a-tooltip>
        </template>
        <template v-if="column.key === 'operation'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm :title="record.status ? '是否禁用该活动？' : ' 是否启用该活动？'"
                          cancel-text="否" ok-text="是"
                          @confirm="() => {confirmChangeStatus(record)}">
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch v-model:checked="record.status" :checked-value="1" :loading="record.loading"
                        :un-checked-value="0" checked-children="已启用" class="flex-shrink-0"
                        size="small" un-checked-children="已禁用" @click="() => {changeStatus(record)}"/>
            </a-popconfirm>
            <a-dropdown placement="bottom" trigger="hover">
              <a-button class="flex items-center" size="small" type="link">
                操作
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="() => handleEdit(record)">编辑秒杀活动</a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm cancel-text="否" ok-text="是"
                                  ok-type="danger"
                                  @confirm="() => {deleteBrand(record.id)}">
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>是否删除秒杀活动？</div>
                        <a-tag class="my-2" color="red">{{ record.activityName }}</a-tag>
                      </template>
                      <a-button danger type="link">删除秒杀活动</a-button>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div>
            <a-button type="link" @click="assignGoods(record)">添加商品</a-button>
            <a-button danger type="text" @click="removeGoods(record)">移除商品</a-button>
          </div>
        </template>
      </template>
    </a-table>

    <FlashSaleModal ref="modalRef" @query-list="queryList"></FlashSaleModal>
    <AddGoodsModal ref="addModalRef" @query-list="queryList"></AddGoodsModal>
    <RemoveGoodsModal ref="removeModalRef" @query-list="queryList"></RemoveGoodsModal>
  </div>
</template>

<style scoped>

</style>