<script setup lang="ts">
import Search from "@/components/Search.vue"
import {getI18nPage, saveOrUpdateTranslation} from "@/api/system/sysI18n.ts";
import {DownOutlined, QuestionCircleOutlined, DeleteOutlined} from "@ant-design/icons-vue";
import { h } from 'vue';

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
  getI18nPage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    pagination.value = {
      current: res.current,
      pageSize: res.size,
      total: res.total,
    }
  })
}
queryList()
const columns = [
  {
    title: '待翻译文本',
    dataIndex: 'rawContent',
    width: 100,
  },
  {
    title: '翻译',
    dataIndex: 'translationList',
    width: 200,
  },
  {
    title: '操作',
    key: 'action',
    width: 100,
  }
]
const dataSource = ref([])
const editContent = ref('')
</script>

<template>
  <div>
    <Search :search-form="searchForm"/>
    <a-table :columns="columns" :data-source="dataSource" :pagination="pagination" row-key="id">
      <template #bodyCell="{ record, index, column }">
        <template v-if="column.dataIndex === 'rawContent'">
          <div class="flex items-start">
            <a-button shape="circle" type="primary" :icon="h(DeleteOutlined)">
            </a-button>

            <a-typography-paragraph copyable :ellipsis="{ rows: 2, expandable: true, symbol: 'more' }">{{ record.rawContent }}</a-typography-paragraph>
          </div>
        </template>
        <template v-if=" column.dataIndex===
          'translationList'">
          <div class="flex items-center" v-for="(item, idx) in record.translationList" :key="idx">
            <a-tag>{{ item.languageCode }}</a-tag>
            <a-typography-paragraph :ellipsis="{ rows: 2, expandable: true, symbol: 'more' }"
                                    :content="item.translatedContent" copyable :editable="{
                tooltip: false,
                onStart: () => {
                  editContent = item.translatedContent
                },
                onChange: (content: any) => {
                  editContent = content
                },
                onEnd: () => {
                  saveOrUpdateTranslation({...item, translatedContent: editContent}).then(() => {
                    queryList()
                  })
                },
                }">
              {{ item.translatedContent }}
            </a-typography-paragraph>
          </div>
        </template>
        <template v-if="column.dataIndex ==='status'">
          <a-switch :checked="record.status" @change="() => { record.status =!record.status; }"></a-switch>
        </template>
        <template v-if="column.key === 'action'">
            <div class="grid grid-cols-2 items-center justify-center">
              <a-popconfirm
                  :cancel-text="$t('否')"
                  :ok-text="$t('是')"
                  :title="
                  record.status
                    ? $t('是否禁用该用户？')
                    : $t(' 是否启用该用户？')
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
              <a-dropdown>
                <a-button class="flex items-center" size="small" type="link"
                >{{ $t('操作') }}
                  <DownOutlined/>
                </a-button>
                <template #overlay>
                  <a-menu class="text-center">
                    <a-menu-item>
                      <a-button
                          type="link"
                          @click="() => saveOrUpdateUserInfo(record, true)"
                      >编辑文本
                      </a-button
                      >
                    </a-menu-item>
                    <a-menu-item>
                      <a-button
                          class="text-orange-300"
                          type="link"
                          @click="handleChangePassword(record)"
                      >{{ $t('重置密码') }}
                      </a-button
                      >
                    </a-menu-item>
                    <a-menu-item>
                      <a-button type="link">{{ $t('更换头像') }}</a-button>
                    </a-menu-item>
                    <a-menu-item>
                      <a-popconfirm
                          :cancel-text="$t('否')"
                          :ok-text="$t('是')"
                          ok-type="danger"
                          @confirm="
                          () => {
                            handleDeleteUser(record.key)
                          }
                        "
                      >
                        <template #icon>
                          <question-circle-outlined style="color: red"/>
                        </template>
                        <template #title>
                          <div>{{ $t('是否删除用户？') }}</div>
                          <a-tag class="my-2" color="red"
                          >{{ record.username }}_{{ record.nickname }}
                          </a-tag
                          >
                        </template>
                        <a-button danger type="link">{{
                            $t('删除用户')
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
  </div>
</template>

<style scoped>
::v-deep(.ant-typography) {
  margin: 0;
}
</style>