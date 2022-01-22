<template>
  <div class="wrapper" :class="{ 'nav-open': $sidebar.showSidebar }">
    <side-bar
      :background-color="sidebarBackground"
      short-title="Cactus"
      title="Cactus"
    >
      <template slot="links">
        <sidebar-item :link="{name: '대시보드', icon: 'ni ni-tv-2 text-primary', path: '/dashboard'}"/>
        <sidebar-item :link="{name: '일정관리', icon: 'ni ni-calendar-grid-58 text-blue', path: '/schedule'}"/>
        <sidebar-item v-if="this.$store.state.user.type === 'CHILD'" :link="{name: '위치', icon: 'ni ni-pin-3 text-orange', path: '/maps'}"/>
        <sidebar-item :link="{name: '쪽지', icon: 'ni ni-email-83 text-purple', path: '/messages'}"/>
        <sidebar-item :link="{name: '개인정보', icon: 'ni ni-single-02 text-yellow', path: '/profile'}"/>
        <sidebar-item :link="{name: '로그아웃', icon: 'ni ni-key-25 text-info', path: '/login'}"/>
      </template>
    </side-bar>
    <div class="main-content" :data="sidebarBackground">

      <div @click="toggleSidebar">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
        <content-footer v-if="!$route.meta.hideFooter"></content-footer>
      </div>
    </div>
  </div>
</template>
<script>
  import ContentFooter from './ContentFooter.vue';
  import { FadeTransition } from 'vue2-transitions';

  export default {
    components: {
      ContentFooter,
      FadeTransition
    },
    data() {
      return {
        sidebarBackground: 'vue' //vue|blue|orange|green|red|primary
      };
    },
    methods: {
      toggleSidebar() {
        if (this.$sidebar.showSidebar) {
          this.$sidebar.displaySidebar(false);
        }
      }
    }
  };
</script>
<style lang="scss">
</style>
