<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
<!--          此部分功能可以自己做-->
<!--          <a-form-item>-->
<!--            <a-input v-model:value="param.name" placeholder="input search categoryname">-->
<!--            </a-input>-->
<!--          </a-form-item>-->
<!--          <a-form-item>-->
<!--            <a-button type="primary" @click="handleQuery()">-->
<!--              查询-->
<!--            </a-button>-->
<!--          </a-form-item>-->

          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              刷新
            </a-button>
          </a-form-item>

          <a-form-item>
            <a-button type="primary" @click="add()">
              新增分类
            </a-button>
          </a-form-item>

        </a-form>

      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
      >
<!--        取消分页操作需要添加pagination变量并设置为false -->
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar"/>
        </template>

        <!--        按钮的渲染 -->
        <template v-slot:action="{ text, record }">
          <!--          空格 -->
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复，是否删除？"
                ok-text="确定"
                cancel-text="取消"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>

      </a-table>
    </a-layout-content>
  </a-layout>

  <!--  Vue3中template下面可以放很多个子节点-->
  <a-modal
      title="分类表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOK"
  >
    <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="名称">
        <a-input v-model:value="category.name"/>
      </a-form-item>
      <a-form-item label="父分类">
        <a-input v-model:value="category.parent"/>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="category.sort"/>
      </a-form-item>
    </a-form>
  </a-modal>

</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'AdminCategory',
  setup() {
    const param = ref();
    param.value = {};
    const categorys = ref();
    const loading = ref(false);

    const columns = [
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父分类',
        key: 'parent',
        dataIndex: 'parent',
      },
      {
        title: '顺序',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    /**
     * 一级分类树，children属性就是二级分类
     * [{
     *    id: "",
     *    name: "",
     *    children: [{
     *        id: "",
     *        name: "",
     *    }]
     * }]
     */
    const level1 = ref(); // 一级分类树，children属性就是二级分类

    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;
      axios.get("/category/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) { // 成功则进行继续查询
          categorys.value = data.content;
          console.log("原始数据：", categorys.value);

          level1.value = [];
          level1.value = Tool.array2Tree(categorys.value,0);
          console.log("树形结构：", level1);
        } else { // 不成功则弹出错误提示框
          message.error(data.message);
        }
      });
    };

    // -------- 表单 --------
    const category = ref({});
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOK = () => {
      modalLoading.value = true;
      axios.post("/category/save", category.value).then((response) => {
        modalLoading.value = false; // 直接取消Loading效果，不会在提示错误之后继续Loading转圈圈
        const data = response.data; // data = commonResp
        if (data.success) {
          modalVisible.value = false;

          // 重新加载列表
          handleQuery();
        } else {
          message.error(data.message); // 提示名称不能为空
        }
      });
    };

    /**
     * 编辑
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      category.value = Tool.copy(record);
    };

    /**
     * 新增
     */
    const add = () => {
      modalVisible.value = true;
      category.value = {};
    };

    /**
     * 删除
     */
    const handleDelete = (id: number) => {
      axios.delete("/category/delete/" + id).then((response) => {
        const data = response.data; // data = commonResp
        if (data.success) {
          // 重新加载列表
          handleQuery();
        }
      });
    };

    onMounted(() => {
      handleQuery();
    });

    return {
      param,
      // categorys,
      level1,
      columns,
      loading,
      handleQuery,

      edit,
      add,

      category,
      modalVisible,
      modalLoading,
      handleModalOK,
      handleDelete
    }
  }
});
</script>

<!-- 封面图片大小 -->
<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>