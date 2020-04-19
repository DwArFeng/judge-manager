import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/home',
    component: () => import('../views/home/Home'),
    children: [
      {
        path: '',
        redirect: 'section',
      },
      {
        path: 'section',
        component: () => import('../views/section/Section'),
      },
      {
        path: 'driver-support',
        component: () => import('../views/driverSupport/DriverSupport'),
      },
      {
        path: 'judger-support',
        component: () => import('../views/judgerSupport/JudgerSupport'),
      },
      {
        path: 'driver-info',
        component: () => import('../views/driverInfo/DriverInfo'),
      },
      {
        path: 'judger-info',
        component: () => import('../views/judgerInfo/JudgerInfo'),
      },
    ],
  },
];

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes,
});

export default router;
