<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue"
import {message, Pagination, PaginationProps} from "ant-design-vue";
import {t} from "@/utils/i18n.ts";
import {deleteSmsBrandById, saveOrUpdateSmsBrand} from "@/api/sms/brand.ts";

const {dataSource, pagination} = defineProps({
  dataSource: Array,
  pagination: Pagination,
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
    width: 80,
    align: 'center',
  },
  {
    title: t('图片'),
    dataIndex: 'logo',
    key: 'logo',
    width: 100,
    align: 'center',
  },
  {
    title: t('品牌名称'),
    dataIndex: 'brandName',
    key: 'brandName',
    width: 200,
    align: 'center',
  },
  {
    title: t('备注'),
    dataIndex: 'remark',
    key: 'remark',
    width: 160,
    align: 'center',
  },
  {
    title: t('排序'),
    dataIndex: 'sort',
    key: 'sort',
    width: 100,
    align: 'center',
  },
  {
    title: t('操作'),
    key: 'operation',
    width: 160,
    fixed: 'right',
  },
]
</script>

<template>
  <a-table
      :columns="columns"
      :dataSource="dataSource"
      :pagination="pagination"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      @change="handleTableChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-else-if="column.dataIndex === 'logo'">
        <a-avatar :src="record.logo" shape="square" size="large"></a-avatar>
      </template>
      <template v-else-if="column.dataIndex === 'brandName'">
        <a-tooltip :arrow="false">
          <template #title>
            <span>{{ record.brandName }}</span>
          </template>
          <span>{{ record.brandName?.substring(0, 20) }}</span>
          <span v-if="record.brandName?.length > 20">...</span>
        </a-tooltip>
      </template>
      <template v-else-if="column.dataIndex === 'platform'">
        {{ record.platform === 1 ? $t('电脑端') : $t('移动端') }}
      </template>
      <template v-else-if="column.dataIndex === 'remark'">
        <a-tooltip :arrow="false">
          <template #title>
            <span>{{ record.remark }}</span>
          </template>
          <span>{{ record.remark?.substring(0, 10) }}</span>
          <span v-if="record.remark?.length > 10">...</span>
        </a-tooltip>
      </template>
      <template v-else-if="column.dataIndex === 'sort'">
        {{ record.sort }}
      </template>

      <template v-else-if="column.key === 'operation'">
        <div class="grid grid-cols-2 items-center justify-center">
          <a-popconfirm
              :cancel-text="$t('否')"
              :ok-text="$t('是')"
              :title="
                record.status ? $t('是否禁用该品牌？') : $t(' 是否启用该品牌？')
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
            <a-switch v-model:checked="record.status" :checked-children="$t('已启用')" :loading="record.loading"
                      :un-checked-children="$t('已禁用')" class="flex-shrink-0" size="small"
                      @click="() => {record.status = !record.status}"/>
          </a-popconfirm>
          <a-dropdown placement="bottom" trigger="hover">
            <a-button class="flex items-center" size="small" type="link"
            >{{ $t('操作') }}
              <DownOutlined/>
            </a-button>
            <template #overlay>
              <a-menu class="text-center">
                <a-menu-item>
                  <a-button type="link" @click="() => emit('openModal',record)">{{
                      $t('编辑推荐品牌')
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
                      <div>{{ $t('是否删除推荐品牌？') }}</div>
                      <a-tag class="my-2" color="red">{{
                          record.brandName
                        }}
                      </a-tag>
                    </template>
                    <a-button danger type="link">{{
                        $t('删除推荐品牌')
                      }}
                    </a-button>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </template>
    </template>
  </a-table>
</template>

<style scoped>

</style>