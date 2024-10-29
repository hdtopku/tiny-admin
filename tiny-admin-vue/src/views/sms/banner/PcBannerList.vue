<script setup lang="ts">
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {t} from "@/utils/i18n.ts";
import {message, Pagination, PaginationProps} from "ant-design-vue";
import {deleteBannerById, saveOrUpdateBanner} from "@/api/sms/banner.ts";

const {dataSource, pagination} = defineProps({
  dataSource: Array,
  pagination: Pagination,
})

const emit = defineEmits(['openModal', 'queryList'])
const handleTableChange = (pagination: PaginationProps) => {
  emit('queryList', {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
}
const confirmChangeStatus = (record: any) => {
  record.loading = true
  const {loading, createTime, updateTime, ...rest} = record
  rest.status =!rest.status
  saveOrUpdateBanner(rest).then(() => {
    record.status = rest.status
    message.success(t('操作成功'))
  }).finally(() => {
    record.loading = false
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
    dataIndex: 'picUrl',
    key: 'picUrl',
    width: 100,
    align: 'center',
  },
  {
    title: t('轮播名称'),
    dataIndex: 'bannerName',
    key: 'bannerName',
    width: 200,
    align: 'center',
  },
  {
    title: t('平台'),
    dataIndex: 'platform',
    key: 'platform',
    width: 100,
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
      <template v-else-if="column.dataIndex === 'picUrl'">
        <img :src="record.picUrl"/>
      </template>
      <template v-else-if="column.dataIndex === 'bannerName'">
        <a-tooltip :arrow="false">
          <template #title>
            <span>{{ record.bannerName }}</span>
          </template>
          <span>{{ record.bannerName?.substring(0, 20) }}</span>
          <span v-if="record.bannerName?.length > 20">...</span>
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
                record.status ? $t('是否禁用该轮播卡片？') : $t(' 是否启用该轮播卡片？')
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
                @click="record.status=!record.status"
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
                  <a-button type="link" @click="() => emit('openModal', record)">{{
                      $t('编辑轮播卡片')
                    }}
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <a-popconfirm
                      :cancel-text="$t('否')"
                      :ok-text="$t('是')"
                      ok-type="danger"
                      @confirm="
                        () => {
                          deleteBannerById(record.id)
                        }
                      "
                  >
                    <template #icon>
                      <question-circle-outlined style="color: red"/>
                    </template>
                    <template #title>
                      <div>{{ $t('是否删除轮播卡片？') }}</div>
                      <a-tag class="my-2" color="red">{{
                          record.brandName
                        }}
                      </a-tag>
                    </template>
                    <a-button danger type="link">{{
                        $t('删除轮播卡片')
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