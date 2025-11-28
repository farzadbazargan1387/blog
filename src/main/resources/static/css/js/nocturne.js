/* tiny helpers for the nocturne UI */
document.addEventListener('DOMContentLoaded', function(){
    // Add enter-to-search focus behaviour: pressing "/" focuses search input
    document.addEventListener('keydown', function(e){
        if(e.key === '/' && document.activeElement.tagName.toLowerCase() !== 'input') {
            e.preventDefault();
            const s = document.querySelector('.ns-search input[type="search"]');
            if(s) s.focus();
        }
    });
});
