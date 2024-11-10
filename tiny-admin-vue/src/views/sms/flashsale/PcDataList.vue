<script lang="ts" setup>
import {DownOutlined} from "@ant-design/icons-vue"
import {Pagination, PaginationProps} from "ant-design-vue";
import {t} from "@/utils/i18n.ts";
import SwitchStatusConfirm from "@/components/SwitchStatusConfirm.vue";

const {dataSource, pagination, loading, queryList=()=>{}, changeRecordStatus=()=>{}, deleteRecordById=()=>{}} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
  queryList: Function,
  changeRecordStatus: Function,
  deleteRecordById: Function,
})
const emit = defineEmits(['openModal', 'openAssignGoodsModal'])
const handleTableChange = (pagination: PaginationProps) => {
  queryList({
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
}
const columns: any = [
  {
    title: t('序号'),
    dataIndex: 'index',
    key: 'index',
    align: 'center',
    width: 60,
  },
  {
    title: t('活动名称'),
    dataIndex: 'activityName',
    key: 'activityName',
    align: 'center',
    width: 120,
  },
  {
    title: t('备注'),
    dataIndex: 'remark',
    key: 'remark',
    width: 160,
  },
  {
    title: t('开始时间'),
    dataIndex: 'startTime',
    key: 'startTime',
    width: 120,
    align: 'center',
  },
  {
    title: t('结束时间'),
    dataIndex: 'endTime',
    key: 'endTime',
    width: 120,
    align: 'center',
  },
  {
    title: t('创建时间'),
    dataIndex: 'createTime',
    key: 'createTime',
    width: 120,
    align: 'center',
  },
  {
    title: t('修改时间'),
    dataIndex: 'updateTime',
    key: 'updateTime',
    width: 120,
    align: 'center',
  },
  {
    title: t('操作'),
    key: 'operation',
    width: 160,
    align: 'center',
    fixed: 'right',
  },
]
</script>

<template>
  <a-table :columns="columns" :data-source="dataSource" :loading="loading" :pagination="pagination"
           :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }" @change="handleTableChange">
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-if="column.key === 'activityName'">
          <span v-if="record.activityName?.length <= 20">{{
              record.activityName
            }}</span>
        <a-tooltip v-else :arrow="false">
          <template #title>
            <span>{{ record.activityName }}</span>
          </template>
          <span>{{ record.activityName?.substring(0, 20) }}</span>
          <span v-if="record.activityName?.length > 20">...</span>
        </a-tooltip>
        <a-tag
        >{{
            $t('活动共包含')
          }}<b class="text-red-500">{{ record?.goodsIds?.length }}</b
          >{{ $t('件商品') }}
        </a-tag
        >
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
          <SwitchStatusConfirm :record="record" @change-record-status="changeRecordStatus(record)"/>
          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link"
            >{{ $t('操作') }}
              <DownOutlined/>
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="emit('openModal',record)">编辑</a-button>
                </a-menu-item>
                <a-menu-item>
                  <DeleteRecordConfirm :record-id="record.id" :record-name="record.activityName"
                                       @delete-record-by-id="deleteRecordById(record.id)"/>
                </a-menu-item>
                <a-menu-item>
                  <a-button class="text-orange-300" type="link" @click="emit('openAssignGoodsModal',record)">分配商品</a-button>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </template>
    </template>
  </a-table>
</template>