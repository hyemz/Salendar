<template>
  <v-footer
    class="footer mt-12"
    :class="{ [`footer-${type}`]: type }"
    :data-background-color="backgroundColor"
  >
    <div class="container">
      <nav>
        <ul>
          <li>
            <router-link to="/salelist">
              <v-icon color="grey">mdi-home-circle</v-icon>HOME
            </router-link>
          </li>
          <li>
            <router-link to="/team">
              <v-icon color="grey">mdi-account-supervisor-circle</v-icon>TEAM
            </router-link>
          </li>
          <li>
            <router-link to="/">
              <v-icon color="grey">mdi-information</v-icon>ABOUT SALENDAR
            </router-link>
          </li>
        </ul>
      </nav>
      <div class="copyright grey--text">
        &copy; {{ year }}, Salendar, Coded by 고영길 김지영 윤혜민 최정휴 현성섭.
      </div>
    </div>
  </v-footer>
</template>
<script>
export default {
  props: {
    backgroundColor: String,
    type: String,
  },
  data() {
    return {
      year: new Date().getFullYear(),
    };
  },
};
</script>
<style scoped>
.footer {
  padding: 10px 0;
  bottom: 0;
  width: 100%;
}
.container {
  height: 100%;
  z-index: 1;
  text-align: center;
  position: relative;
  width: 100%;
  padding-right: 10px;
  padding-left: 20px;
  margin-right: auto;
  margin-left: auto;
}
.footer .copyright {
  text-align: right;
}
.footer ul {
  margin-bottom: 0;
  padding: 0;
  list-style: none;
}
.footer ul li {
  display: inline-block;
}
li {
  margin: 0 0;
  padding: 0;
}
.footer nav {
  display: inline-block;
  float: left;
}
a {
  cursor: pointer;
  text-decoration: none;
  padding: 8px;
  color: grey !important;
}
strong {
  color: #08376b !important;
}
</style>
