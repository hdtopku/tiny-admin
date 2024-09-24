<script lang="ts" setup>
import {Ref, ref} from 'vue';
import {message} from "ant-design-vue";
import ImageUpload from "@/components/ImageUpload.vue";
import RichTextEditor from "@/components/RichTextEditor.vue";
import {saveOrUpdateGoods} from "@/api/goods.ts";

const open = ref<boolean>(false);
const isUpdate = ref<boolean>(false);
const goodsInfo = ref<any>({});
const rules: Ref<any> = ref({
  goodsName: [
    {required: true, message: '请输入商品名称', trigger: ['blur', 'change']},
    {min: 1, max: 50, message: '长度在 2 到 30 个字符', trigger: ['blur', 'change']}
  ],
  goodsDesc: [
    {required: true, message: '请输入商品描述', trigger: ['blur', 'change']},
    {min: 1, max: 200, message: '长度在 1 到 100 个字符', trigger: ['blur', 'change']}
  ],
  promotionPrice: [
    {required: true, message: '请输入促销价', trigger: ['blur', 'change']},
    {type: 'number', message: '请输入数字', trigger: ['blur', 'change']}
  ],
  stock: [
    {required: true, message: '请输入库存', trigger: ['blur', 'change']},
    {type: 'number', message: '请输入数字', trigger: ['blur', 'change']}
  ],
})
const formRef = ref()
const formLoading = ref(false)
const emits = defineEmits(['queryList']);
const handleOk = () => {
  formRef.value.validate().then(() => {
    formLoading.value = true;
    saveOrUpdateGoods(goodsInfo.value).then(() => {
      message.success("操作成功");
      open.value = false
      emits('queryList')
    }).finally(() => {
      formLoading.value = false
    })
  }).catch((err) => {
    console.log('error', err)
  })
}
const activeKey = ref('basicInfo');

const showModal = (goods: any = {}) => {
  if (goods.id) {
    isUpdate.value = true;
    goodsInfo.value = Object.assign({}, goods);
  } else {
    goodsInfo.value = {}
  }
  open.value = true;
}

defineExpose({
  showModal
})

watch(goodsInfo, (newVal)=>{
  console.log(newVal)
})
</script>
<template>
  <div>
    <a-modal v-model:open="open" :title="isUpdate? '更新商品' : '新增商品'" cancel-text="取消" destroy-on-close
             ok-text="提交"
             @ok="handleOk">
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">取消</a-button>
          <a-button key="submit" :loading="formLoading" type="primary" @click="handleOk">提交</a-button>
        </a-space>
      </template>
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="basicInfo" tab="基本信息"></a-tab-pane>
        <a-tab-pane key="detailInfo" tab="商品详情" force-render></a-tab-pane>
      </a-tabs>
      <div style="height: 400px; overflow: auto;">
      <a-form v-show="activeKey === 'basicInfo'" ref="formRef" :label-col="{span: 4}" :model="goodsInfo" :rules="rules">
        <a-form-item label="商品图册" name="logo">
          <div class="flex justify-center">
            <ImageUpload v-model:image-urls="goodsInfo.albumList" :count="2"></ImageUpload>
          </div>
        </a-form-item>
        <a-form-item label="商品名称" name="goodsName">
          <a-input v-model:value="goodsInfo.goodsName" allowClear placeholder="请输入品牌名称"/>
        </a-form-item>
        <a-form-item label="商品简介" name="goodsDesc">
          <a-textarea v-model:value="goodsInfo.goodsDesc" :rows="2" allowClear placeholder="请简单介绍一下商品"/>
        </a-form-item>
        <div class="flex justify-around">
          <a-form-item :label-col="{span: 12}" label="促销价" name="promotionPrice">
            <a-input-number v-model:value="goodsInfo.promotionPrice" :max="99999999" :min="0" :precision="2"/>
          </a-form-item>
          <a-form-item :label-col="{span: 12}" label="市场价" name="marketPrice">
            <a-input-number v-model:value="goodsInfo.marketPrice" :max="99999999" :min="0" :precision="2"/>
          </a-form-item>
        </div>
        <div class="flex justify-around">
          <a-form-item :label-col="{span: 12}" label="库存" name="stock">
            <a-input-number v-model:value="goodsInfo.stock" :max="99999999" :min="0" default-value="9999"/>
          </a-form-item>
          <a-form-item :label-col="{span: 12}" label="预警库存" name="lowStock">
            <a-input-number v-model:value="goodsInfo.lowStock" :max="99999999" :min="0" default-value="0"/>
          </a-form-item>
        </div>
      </a-form>
      <!-- 商品详情 -->
      <RichTextEditor v-show="activeKey === 'detailInfo'" v-model:model-value="goodsInfo.detailHtml"></RichTextEditor>
      </div>
    </a-modal>
  </div>
</template>

