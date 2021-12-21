<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
      >
        <a-sub-menu key="sub1">
          <template #title>
              <span>
                <user-outlined/>
                subnav 1
              </span>
          </template>
          <a-menu-item key="1">option1</a-menu-item>
          <a-menu-item key="2">option2</a-menu-item>
          <a-menu-item key="3">option3</a-menu-item>
          <a-menu-item key="4">option4</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <template #title>
              <span>
                <laptop-outlined/>
                subnav 2
              </span>
          </template>
          <a-menu-item key="5">option5</a-menu-item>
          <a-menu-item key="6">option6</a-menu-item>
          <a-menu-item key="7">option7</a-menu-item>
          <a-menu-item key="8">option8</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub3">
          <template #title>
              <span>
                <notification-outlined/>
                subnav 3
              </span>
          </template>
          <a-menu-item key="9">option9</a-menu-item>
          <a-menu-item key="10">option10</a-menu-item>
          <a-menu-item key="11">option11</a-menu-item>
          <a-menu-item key="12">option12</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-list item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }" :pagination="pagination" :data-source="ebooks">
<!--        :grid="{ gutter: 20, column: 3 }" gutter表示列之间的间隔，column表示列的数量-->
<!--        :pagination="pagination" 此代码为分页-->
        <!--        <div slot="footer"><b>ant design vue</b> footer part</div>-->
        <!--        已经弃用-->
        <!-- old -->
        <!--        <img slot="item-icon" src="../assets/img/tabbar/home.svg" alt="">-->
        <!-- 弃用 -->

        <!-- new -->
        <!--        <template v-slot:item-icon><img src="../assets/img/tabbar/home.svg" alt=""></template>-->
        <!-- 提供缩写# -->
        <!--        <template #item-icon><img src="../assets/img/tabbar/home.svg" alt=""></template>-->

        <!--        <template #footer>-->
        <!--          <div><b>ant design vue</b> footer part</div>-->
        <!--        </template>-->
        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
          <span v-for="{ type, text } in actions" :key="type">
          <component v-bind:is="type" style="margin-right: 8px"/>
          {{ text }}
          </span>
            </template>
<!--            描述图片-->
<!--            <template #extra>-->
<!--              <img-->
<!--                  width="272"-->
<!--                  alt="logo"-->
<!--                  src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"-->
<!--              />-->
<!--            </template>-->
            <a-list-item-meta :description="item.description">
              <template #title>
<!--                herf内容表示页面跳转-->
<!--                name表示电子书名字-->
                <a :href="item.href">{{ item.name }}</a>
              </template>
              <template #avatar>
<!--                avatar表示图标封面-->
                <a-avatar :src="item.cover"/>
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, reactive, toRef} from 'vue';
import axios from 'axios';

const listData: any = [];
for (let i = 1; i <= 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    description:
        'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  });
}

export default defineComponent({
  name: 'Home',
  setup() { // vue3新增的函数，初始化的时候会调用
    console.log("setup");
    const ebooks = ref();
    const ebooks1 = reactive({books: []});
    // axios.get("http://localhost:8085/ebook/List?name=Spring").then(function (response){
    //   console.log(response)


    onMounted(() => { //生命周期函数，尽量将初始化函数放到里面
      console.log("onMounted")
      // "http://localhost:8085/ebook/List?name=Spring" 只会显示一个，需要将?name=Spring删除
      axios.get("http://localhost:8085/ebook/List").then((response) => {
        const data = response.data;
        ebooks.value = data.content;
        ebooks1.books = data.content;
        console.log(response)
      });
    })

    return {
      ebooks,
      ebooks2: toRef(ebooks1, "books"),
      listData,
      pagination: {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions: [
        {type: 'star-o', text: '156'},
        {type: 'like-o', text: '156'},
        {type: 'message', text: '2'},
      ],
    }
  }
});
</script>


<style scoped>
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>