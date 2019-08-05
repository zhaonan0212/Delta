This folder, **mes-mapping-template**, will be used to managed the source code and distribution of 填表小幫手.
Every objects (bas, cls, frm) in excel file(e.g. xlsm) will be extracted by pre-commit hook automatically. 

```
MESA2
│   README.MD    
└───mes-mapping-template
│   │   README.MD
│   │   CDM-MappingTemplate.xlsm
│   │
│   └───CDM-MappingTemplate.src.vba
│       │   *.bas
│       │   *.cls
│       │   *.frm
│   
└───...
```

## Git hooks ##
There are a few downsides to using Excel events. You are dependent on Excel, so if you copy your workbook from an email or another folder into your Git repository folder, your VBA export function will not run. Distributing the export function (either via copy and paste or as an Addin) and ensuring it runs reliably is another pain point.

An alternative approach is to exploit Git’s built-in hooks. Hooks are programs you can place in a hooks directory to trigger actions at certain points in Git’s execution. You can find a list of available hooks in https://git-scm.com/docs/githooks. We will use the pre-commit hook to do the following when you call git commit:

extract the VBA modules from your workbook and write them as .bas or .cls or .frm files to your repository
add these VBA files to your commit via git add -- ./src.vba
finally execute the git commit command

1. 安裝python 3.6 or above
2. pip install -U oletools
3. 建立Git Hook (pre-commit)
```bash
#!/bin/sh
python .git/hooks/pre-commit.py
git add -- ./src.vba
```