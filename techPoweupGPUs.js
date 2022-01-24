// https://www.techpowerup.com/gpu-specs/?mobile=No&workstation=No&igp=No&sort=name

let printNames = () => {
	var amd = $$("#list > table > tbody > tr > td.vendor-AMD > a").map((a) => {return a.innerText});
	var intel = $$("#list > table > tbody > tr > td.vendor-Intel > a").map((a) => {return a.innerText});
	var nvidia = $$("#list > table > tbody > tr > td.vendor-NVIDIA > a").map((a) => {return a.innerText});

	var all = ['AMD', ...amd, '\n', 'Intel', ...intel, '\n', 'Nvidia',  ...nvidia];
	console.log(all.join('\n- '));
}

