<#import "../layout.ftlh" as main>
<@main.layout ; spring>
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Статистика</h5>
                    <form action="/analytics/statistics" id="period">
                        <div class="form-group my-2">
                            <label for="from" class="form-label">От:</label>
                            <input type="date" id="from" class="form-control" name="from">
                        </div>
                        <div class="form-group my-2">
                            <label for="to" class="form-label">До:</label>
                            <input type="date" id="to" class="form-control" name="to">
                        </div>
                        <button type="submit" hidden id="periodButton"></button>
                    </form>
                </div>
            </div>
    <script src="/js/statistics.js">
    </script>
</@main.layout>
