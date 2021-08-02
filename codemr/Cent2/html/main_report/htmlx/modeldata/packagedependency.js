var matrix = [[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,2,0,2,0,0,2,0,0,0],[0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0],[0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,3,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,6,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1,0,1,3,0,0,0],[0,2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0],[0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,3,0,1,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0],[0,0,1,0,0,1,0,0,0,1,1,0,4,0,0,1,1,1,0,5,1,0,6,0,1,1],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,4,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,3,0,0,0],[0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0],[0,4,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,2,0,0,3,0,1,0],[0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0],[0,2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0],[0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0]]
var packages = [{
"name": " bomb.components.simon.screams", "color": " #3182bd"
}
,{
"name": " bomb", "color": " #6baed6"
}
,{
"name": " bomb.modules.il", "color": " #9ecae1"
}
,{
"name": " bomb.modules.wz", "color": " #c6dbef"
}
,{
"name": " bomb.tools.data.structures.dictionary", "color": " #e6550d"
}
,{
"name": " bomb.modules.ab", "color": " #fd8d3c"
}
,{
"name": " bomb.tools.data.structures", "color": " #fdae6b"
}
,{
"name": " bomb.tools.data.structures.hash", "color": " #fdd0a2"
}
,{
"name": " bomb.tools.data.structures.graph.matrix", "color": " #31a354"
}
,{
"name": " bomb.modules.s", "color": " #74c476"
}
,{
"name": " bomb.modules.t", "color": " #a1d99b"
}
,{
"name": " bomb.modules.dh.hexamaze.hexalgorithm", "color": " #c7e9c0"
}
,{
"name": " bomb.interfaces", "color": " #756bb1"
}
,{
"name": " bomb.sub.controllers", "color": " #9e9ac8"
}
,{
"name": " bomb.components.simon.states", "color": " #bcbddc"
}
,{
"name": " bomb.modules.c", "color": " #dadaeb"
}
,{
"name": " bomb.modules.r", "color": " #636363"
}
,{
"name": " bomb.modules.dh", "color": " #969696"
}
,{
"name": " bomb.modules.m", "color": " #bdbdbd"
}
,{
"name": " bomb.tools", "color": " #d9d9d9"
}
,{
"name": " bomb.modules.np", "color": " #3182bd"
}
,{
"name": " bomb.tools.data.structures.graph.list", "color": " #6baed6"
}
,{
"name": " bomb.enumerations", "color": " #9ecae1"
}
,{
"name": " bomb.tools.data.structures.avl", "color": " #c6dbef"
}
,{
"name": " bomb.components.hex", "color": " #e6550d"
}
,{
"name": " bomb.modules.t.translated", "color": " #fd8d3c"
}
];
