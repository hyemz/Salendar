<template>
  <v-app>
    <!-- 헤더 -->
    <Toolbar/>
    

      

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import Toolbar from '@/components/Toolbar'

export default {
  name: 'App',

  components: {
    Toolbar,
  },

  data: () => ({
    //
  }),
};
</script>
