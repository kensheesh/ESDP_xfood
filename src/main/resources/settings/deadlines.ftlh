<#import "layout.ftlh" as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 shadow-sm border-0">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <#assign userRole = authorizedUser.authorities[0].authority>
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="#" class="btn disabled rounded-4 w-100 h-100 btn-dodo-light">
                                        Дедлайны
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        Токены
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        Шаблоны
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-xl-9">
            <div>

            </div>
        </div>
    </div>

    <script>
        function handleEditClick(selectedValue) {
            window.location.href = '#' + selectedValue
        }

        function handleDeleteClick(selectedValue) {
            window.location.href = '#' + selectedValue
        }
    </script>

</@main.layout>