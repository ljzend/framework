<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />
  </div>
</template>

<script>
import GithubCorner from '@/components/GithubCorner'

export default {
  name: 'DashboardAdmin',
  components: {
    GithubCorner,
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  //padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;
  height: calc(100vh - 85px);

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
