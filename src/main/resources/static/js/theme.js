(function () {

    const THEME_KEY = "scs-theme";

    function getPreferredTheme() {
        const saved = localStorage.getItem(THEME_KEY);
        if (saved === "dark" || saved === "light") {
            return saved;
        }
        return window.matchMedia("(prefers-color-scheme: dark)").matches
            ? "dark"
            : "light";
    }

    function applyTheme(theme) {
        document.body.classList.toggle("dark-mode", theme === "dark");
        const btn = document.getElementById("themeToggle");
        if (btn) {
            btn.textContent = theme === "dark" ? "☀️" : "🌙";
        }
    }

    function toggleTheme() {
        const current = document.body.classList.contains("dark-mode") ? "dark" : "light";
        const next = current === "dark" ? "light" : "dark";
        localStorage.setItem(THEME_KEY, next);
        applyTheme(next);
    }

    window.toggleTheme = toggleTheme;

    document.addEventListener("DOMContentLoaded", function () {
        applyTheme(getPreferredTheme());
        const btn = document.getElementById("themeToggle");
        if (btn) {
            btn.addEventListener("click", toggleTheme);
        }
    });

})();