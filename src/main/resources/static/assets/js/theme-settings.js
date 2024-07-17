!(function() {
    function r(a) {
        var b;
        document.getElementById("header-lang-img") &&
            ("en" == a ? document.getElementById("header-lang-img").src = "assets/images/flags/us.svg" :
                "sp" == a ? document.getElementById("header-lang-img").src = "assets/images/flags/spain.svg" :
                "gr" == a ? document.getElementById("header-lang-img").src = "assets/images/flags/germany.svg" :
                "it" == a ? document.getElementById("header-lang-img").src = "assets/images/flags/italy.svg" :
                "ru" == a ? document.getElementById("header-lang-img").src = "assets/images/flags/russia.svg" :
                "ch" == a ? document.getElementById("header-lang-img").src = "assets/images/flags/china.svg" :
                "fr" == a && (document.getElementById("header-lang-img").src = "assets/images/flags/french.svg"));
    }

    function s() {
        var a;
        document.querySelectorAll(".navbar-nav .collapse") &&
            ((a = document.querySelectorAll(".navbar-nav .collapse")),
                Array.from(a).forEach(function(a) {
                    var b = new bootstrap.Collapse(a, { toggle: !1 });
                    a.addEventListener("show.bs.collapse", function(c) {
                        c.stopPropagation();
                        var d, c = a.parentElement.closest(".collapse");
                        c && (d = bootstrap.Collapse.getInstance(c)) && d.hide()
                    })
                }))
    }

    function t() {
        var a, d = document.documentElement.getAttribute("data-layout"),
            b = JSON.parse(sessionStorage.getItem("defaultAttribute"));
        b && "twocolumn" == d || "twocolumn" == b["data-layout"] && document.querySelector('.btn[data-bs-target="#theme-settings-offcanvas"]') && a.click();
        t();
    }

    // Set default theme settings
    document.documentElement.setAttribute("data-layout", "vertical");
    document.documentElement.setAttribute("data-topbar", "blue");
    document.documentElement.setAttribute("data-sidebar", "light");
    document.documentElement.setAttribute("data-sidebar-size", "lg");

    document.addEventListener("DOMContentLoaded", function() {
        s();
        t();
        var i = document.querySelectorAll(".theme-choice input");
        Array.from(i).forEach(function(a) {
            a.addEventListener("change", function() {
                document.documentElement.setAttribute("data-layout-mode", a.value);
            })
        })
    });
})();
