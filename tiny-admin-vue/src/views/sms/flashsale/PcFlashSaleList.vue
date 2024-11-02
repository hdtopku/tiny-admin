<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue"
import {message, Pagination, PaginationProps} from "ant-design-vue";
import {deleteSmsBrandById, saveOrUpdateSmsBrand} from "@/api/sms/brand.ts";
import {t} from "@/utils/i18n.ts";
import {assignGoods, removeGoods} from "@/api/sms/flashGoodsRel.ts";

const {dataSource, pagination, loading} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
})

const emit = defineEmits(['openModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.loading = true
  const {loading, brandName, logo, createTime, updateTime, ...rest} = record
  rest.status = !rest.status
  saveOrUpdateSmsBrand(rest).then(() => {
    record.status = rest.status
    message.success(t('操作成功'))
  }).finally(() => {
    record.loading = false
  })
}
const handleTableChange = (pagination: PaginationProps) => {
  emit('queryList', {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
}
const deleteRecordById = (id: string) => {
  deleteSmsBrandById(id).then(() => {
    message.success(t('删除成功'))
    emit('queryList')
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
          }}<b class="text-red-500">{{ record.goodsIds.length }}</b
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
          <a-popconfirm
              :cancel-text="$t('否')"
              :ok-text="$t('是')"
              :title="
                record.status ? $t('是否禁用该活动？') : $t(' 是否启用该活动？')
              "
              @confirm="
                () => {
                  confirmChangeStatus(record)
                }
              "
          >
            <template #icon>
              <question-circle-outlined style="color: red"/>
            </template>
            <a-switch
                v-model:checked="record.status"
                :checked-children="$t('已启用')"
                :loading="record.loading"
                :un-checked-children="$t('已禁用')"
                class="flex-shrink-0"
                size="small"
                @click="
                  () => {
                    changeStatus(record)
                  }
                "
            />
          </a-popconfirm>
          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link"
            >{{ $t('操作') }}
              <DownOutlined/>
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="emit('openModal',record)">{{
                      $t('编辑秒杀活动')
                    }}
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <a-popconfirm :cancel-text="$t('否')" :ok-text="$t('是')" ok-type="danger"
                                @confirm="deleteRecordById(record.id)">
                    <template #icon>
                      <question-circle-outlined style="color: red"/>
                    </template>
                    <template #title>
                      <div>{{ $t('是否删除秒杀活动？') }}</div>
                      <a-tag class="my-2" color="red">{{
                          record.activityName
                        }}
                      </a-tag>
                    </template>
                    <a-button danger type="link">{{
                        $t('删除秒杀活动')
                      }}
                    </a-button>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
        <div>
          <a-button type="link" @click="assignGoods(record)">{{
              $t('添加商品')
            }}
          </a-button>
          <a-button danger type="text" @click="removeGoods(record)">{{
              $t('移除商品')
            }}
          </a-button>
        </div>
      </template>
    </template>
  </a-table>
</template>

<style scoped>

</style>