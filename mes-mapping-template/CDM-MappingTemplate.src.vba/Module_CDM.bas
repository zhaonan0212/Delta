'-- Modification History
'-- Module 填表小幫手
'-- Version: 1.0

'--Option Explicit的意義是在程式模組中強迫每個在程式模組裏的變數都必須明確完整的宣告和定義其名稱,範圍與型別.
'--Option Explicit 陳述式必須出現在模組中所有程序之前(即最頂端)
'--當 Option Explicit 在模組中使用時，所有的變數必須以 Dim， Private、Public、ReDim 或 Static 陳述式來明確宣告。
'--在模組中如果使用Option Explicit ,在程式中如未宣告變數的名稱，則在編譯階段時，會發生錯誤。
'--如果沒有使用 Option Explicit 陳述式，則所有未宣告的變數都是 Variant 型態，除非預設的型態另以 Deftype 陳述式所指定過。
'--Option Explicit 可避免打錯變數名稱或在有效範圍內設定兩個相同的變數名稱。
Option Explicit

'TableInfo最大行數
Const MAX_ITTABLE_LEN = 500
'ColumnInfo最大行數
Const MAX_TABLECOL_LEN = 5000
Const MAX_COL_NUMBER = 1000
Const MAX_SOURCE_COLUMN_NUM = 100
Const PGM_PASSWORD = "zaq12wsx"

Type SYSTEM_TBL
    System As String
    Table As String
    Type As String
    Condition As String
    ColumnCheck As Boolean
End Type
Sub Check_CDM()
    Dim Status_Base As Boolean
    Dim Status_Detail As Boolean
    Dim SystemTables(MAX_ITTABLE_LEN) As SYSTEM_TBL
    Dim xlErrSheet As Worksheet
    Dim ctrErrBase As Integer
    Dim ctrErrDetail As Integer
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    
    Application.ScreenUpdating = False
    Application.StatusBar = ""
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    
    'Call xlErrSheet.Unprotect(PGM_PASSWORD)
    'Call xlWorksheet1.Unprotect(PGM_PASSWORD)
    'Call xlWorksheet2.Unprotect(PGM_PASSWORD)
    
    Call Check_CDM_FILE_Initial(ThisWorkbook)
     '場宎趙惆劑珜醱
    Call Check_CDM_FILE_Initial(ThisWorkbook)
    '1 潰聆斛沓戲弇祥夔峈諾
    Call Check_CDM_FILE_IsBlank(ThisWorkbook)
    '2 潰聆掘爺爛癹
    Call CHECK_ARCHIVE_PERIOD
    '3 潰聆悵隱爛癹
    Call CHECK_KEEP_PERIOD
    '4 潰聆蚘眊ぐ迡
    Call CHECK_AD_FORMAT
    '5 潰脤tableName岆瘁雛逋a.b=c
    Call CHECK_ISA95_TABLE_SYNTAX
    '7 潰脤等啋跡囀�搧釋那韃堀�
    Call CHECK_SQUARE_BREAK_PAIR
    '8,9 潰脤UK睿FK
    Call CHECK_UK_SYNTAX
    Call CHECK_FK_SYNTAX
    '10,11,12 潰脤tablebinfo睿ColumnInfo腔tableName摯戲弇靡備腔笢嬤瘍爵囀��
    Call CHECK_ISA95_TABLENAME_VAR
    Call CHECK_ISA95_COLUMNNAME_VAR
    Call CHECK_ISA95_COLUMNVALUE_VAR
    '13 潰脤tableInfo腔2,3,4,5蹈岆瘁衄笭葩
    Call CHECK_TABLE_DUP
    '14 潰脤columninfo笢腔table趼僇祥笭葩
    Call CHECK_COLUMN_DUP
    '15 潰脤等啋跡囀�搚遛妅甽閛�
    Call CHECK_MULTILINE_SUPPORT
    '16,17 潰脤table腔name婓column笢岆瘁衄穢迡,眕摯column桶笢岆瘁衄桶靡ぐ渣
    Call CHECK_COLUMN_UNDEF
    Call CHECK_TABLE_UNDEF
    '18,19,20,21,22潰聆domain睿subdomain
    Call CheckSubDomain
    
    
    Status_Base = Check_IT_FILE_Base(ThisWorkbook, SystemTables())
    'Status_Detail = Check_IT_FILE_Detail(ThisWorkbook, SystemTables())
    
    Worksheets("CheckReport").Cells(2, 3).Value = "已檢查，無錯誤"
    
    'If Status_Base = False And Status_Detail = False Then
    '    ' Checking Status -> ok
    '    Worksheets("CheckReport").Cells(2, 3).Value = "已檢查，無錯誤"
    'Else
    '    ' Checking Status -> Error
    '    Worksheets("CheckReport").Cells(2, 3).Value = "已檢查，有錯誤"
    'End If
    
    ctrErrBase = xlErrSheet.Range("B65536").End(xlUp).Row - 7
    ctrErrDetail = xlErrSheet.Range("E65536").End(xlUp).Row - 7
    
    If xlErrSheet.Cells(2, 3).Value = "未檢查" Then
        xlErrSheet.Cells(3, 3).Value = ""
        xlErrSheet.Cells(4, 3).Value = ""
    Else
        xlErrSheet.Cells(3, 3).Value = CStr(ctrErrBase + ctrErrDetail)
        xlErrSheet.Cells(4, 3).Value = Format(Now, "yyyy/mm/dd hh:mm:ss")
        xlErrSheet.Cells(4, 3).NumberFormatLocal = "yyyy/mm/dd hh:mm:ss"
    End If
    
    'Call xlErrSheet.Protect(PGM_PASSWORD, True, True, True, , True, False, False, False, False, False, False, False, False, False, False)
    'Call xlWorksheet1.Protect(PGM_PASSWORD, True, True, True, , True, False, True, False, False, False, False, False, False, False, False)
    'Call xlWorksheet2.Protect(PGM_PASSWORD, True, True, True, , True, False, True, False, False, False, False, False, False, False, False)
    
    Worksheets("CheckReport").Activate
    
    Application.ScreenUpdating = True
    Application.StatusBar = ""
End Sub

Sub Check_CDM_FILE_Initial(xlWorkbook As Workbook)
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim i As Integer
    Dim j As Integer
    
    Set xlErrSheet = xlWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = xlWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = xlWorkbook.Sheets("ColumnInfo")
    
    With xlErrSheet
        
        ' Clean Up the Check Report Header
        .Cells(2, 3).Value = "未檢查"
        .Cells(3, 3).Value = ""
        .Cells(4, 3).Value = ""

        ' Clean Up the Check Report 表格檔案基本資訊(TableInfo)
        For i = 1 To MAX_ITTABLE_LEN
            .Cells(i + 7, 2).Value = ""
            .Cells(i + 7, 3).Value = ""
        Next
        
        
        ' Clean Up the Check Report 表格檔案欄位資訊(ColumnInfo)
        For i = 1 To MAX_TABLECOL_LEN
            For j = 4 To 6
                .Cells(i + 7, j).Value = ""
            Next
        Next
        
    End With
    
    With xlWorksheet1
        'https://access-excel.tips/excel-vba-color-code-list/
        'color index values for reference
        With .Range(.Cells(4, 2), .Cells(MAX_ITTABLE_LEN + 4, 5))
            ' 20
            .Interior.ColorIndex = 19
        End With
    End With
    
    With xlWorksheet2
        'https://access-excel.tips/excel-vba-color-code-list/
        'color index values for reference
        With .Range(.Cells(5, 2), .Cells(MAX_TABLECOL_LEN + 5, 6))
            ' -4142
            .Interior.ColorIndex = 19
        End With
    End With
    
End Sub
'1 準諾瓚剿
Sub Check_CDM_FILE_IsBlank(xlWorkbook As Workbook)
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim m As Integer, n As Integer
    Dim x As Integer, y As Integer
    Dim a As Integer, b As Integer
    Dim arrTable, arrColumn
    
    Set xlErrSheet = xlWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = xlWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = xlWorkbook.Sheets("ColumnInfo")
    arrTable = Array(2, 3, 4, 5, 8, 9, 10, 11, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25)
    arrColumn = Array(2, 3, 4, 5, 6, 9, 11, 12, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)
    
    'check tableInfo is blank
    a = 8
    For m = 4 To xlWorksheet1.[B65536].End(xlUp).Row
        For n = 0 To UBound(arrTable)
            If Trim(xlWorksheet1.Cells(m, arrTable(n))) = "" Then
                xlErrSheet.Cells(a, 2) = "CHECK_VALUE_REQUIRED"
                xlErrSheet.Cells(a, 3) = "森戲弇斛沓,祥褫峈諾,The cells " & m & "," & n & " is blank"
                xlWorksheet1.Cells(m, arrTable(n)).Interior.ColorIndex = 3
                a = a + 1
            Else
                xlWorksheet1.Cells(m, arrTable(n)).Interior.ThemeColor = xlThemeColorAccent4
            End If
         Next n
    Next m
    
    'check columnInfo is blank
    b = 8
    For x = 4 To xlWorksheet2.[B65536].End(xlUp).Row
        For y = 0 To UBound(arrColumn)
            If Trim(xlWorksheet2.Cells(x, arrColumn(y))) = "" Then
                xlErrSheet.Cells(b, 5) = "CHECK_VALUE_REQUIRED"
                xlErrSheet.Cells(b, 6) = "森戲弇斛沓,祥褫峈諾,The cells " & x & "," & y & " is blank"
                xlWorksheet2.Cells(x, arrColumn(y)).Interior.ColorIndex = 3
                b = b + 1
            Else
                xlWorksheet2.Cells(x, arrColumn(y)).Interior.ThemeColor = xlThemeColorAccent4
            End If
         Next y
    Next x
End Sub
'2 寥紫笚ぶ跡宒潰聆
Sub CHECK_ARCHIVE_PERIOD()
    Dim Reg As New RegExp
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim n As Integer, m As Integer, result As Boolean
    Dim n1 As Integer
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    
    n = xlWorksheet1.[L65535].End(xlUp).Row
    n1 = xlErrSheet.[B65535].End(xlUp).Row + 1
    
    With Reg
    .Global = True
    .IgnoreCase = True
    .Pattern = "[0-9]+[DWMQY]"
    End With

    For m = 4 To n
        If (xlWorksheet1.Cells(m, 12)) = "" Or (xlWorksheet1.Cells(m, 12)) = "[祥隅ぶ]" Or (xlWorksheet1.Cells(m, 12)) = "[祥�銩廅" Then
                     
        Else
            result = Reg.Test(xlWorksheet1.Cells(m, 12))
            If result = False Then
                xlWorksheet1.Cells(m, 12).Interior.ColorIndex = 3
                xlErrSheet.Cells(n1, 2) = "CHECK_ARCHIVE_PERIOD"
                xlErrSheet.Cells(n1, 3) = "寥紫笚ぶ跡宒潰脤渣昫 cells(" & m & ",12),��沓��[�菁牻[D,W,M,Q,Y]麼[祥隅ぶ]麼[祥�銩廅麼[�o揃蹋]﹝"
            End If
        End If
    Next m
End Sub
'3 悵隱爛癹跡宒潰聆
Sub CHECK_KEEP_PERIOD()
    Dim Reg As New RegExp
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim n As Integer, m As Integer, result As Boolean
    Dim n1 As Integer
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    
    n = xlWorksheet1.[M65535].End(xlUp).Row
    n1 = xlErrSheet.[B65535].End(xlUp).Row + 1
    
    With Reg
    .Global = True
    .IgnoreCase = True
    .Pattern = "[0-9]+[DWMQY]"
    End With
    
     For m = 4 To n
        If (xlWorksheet1.Cells(m, 13)) = "" Or (xlWorksheet1.Cells(m, 13)) = "[祥隅ぶ]" Or (xlWorksheet1.Cells(m, 13)) = "[祥悵隱]" Then
           
        Else
            result = Reg.Test(xlWorksheet1.Cells(m, 13))
            If result = False Then
                xlWorksheet1.Cells(m, 13).Interior.ColorIndex = 3
                xlErrSheet.Cells(n1, 2) = "CHECK_KEEP_PERIOD"
                xlErrSheet.Cells(n1, 3) = "悵隱爛癹跡宒潰脤渣昫 cells(" & m & ",13),��沓��[�菁牻[D,W,M,Q,Y]麼[祥隅ぶ]麼[祥�銩廅麼[�o揃蹋]﹝"
            End If
        End If
       
    Next m
End Sub
' 4 妏蚚淏寀桶湛宒潰脤蚘眊
Sub CHECK_AD_FORMAT()
    Dim Reg As New RegExp
    Dim n As Integer, n2 As Integer, i As Integer, n1 As Integer, j As Integer
    Dim result As Boolean, content As String
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    'Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    
    n = xlWorksheet1.[X65535].End(xlUp).Row
    n1 = xlErrSheet.[B65535].End(xlUp).Row + 1
    
    With Reg
    .Global = True
    .IgnoreCase = True
    .Pattern = "^[a-zA-Z0-9\.]+$"
    End With
    
    '潰聆沓迡��
    For i = 4 To n Step 1
        content = Trim(xlWorksheet1.Cells(i, 24))
        result = Reg.Test(content)
        If result = False Then
            xlErrSheet.Cells(n1, 2) = "CHECK_AD_FORMAT"
            xlErrSheet.Cells(n1, 3) = "沓迡��:" & Cells(i, 24) & "蚘眊ぐ迡渣昫,��沓���T馱E-MAIL(祥婦漪@deltaww.com) "
            xlWorksheet1.Cells(i, 24).Interior.ColorIndex = 3
            n1 = n1 + 1
        End If
    Next i
    
    '潰聆薊釐敦諳
    For j = 4 To n Step 1
        content = Trim(xlWorksheet1.Cells(j, 16))
        result = Reg.Test(content)
        If result = False Then
            xlErrSheet.Cells(n1, 2) = "CHECK_AD_FORMAT"
            xlErrSheet.Cells(n1, 3) = "薊釐敦諳:" & Cells(j, 16) & " ぐ迡渣昫,��沓���T馱E-MAIL(祥婦漪@deltaww.com) "
            xlWorksheet1.Cells(j, 16).Interior.ColorIndex = 3
            n1 = n1 + 1
        End If
    Next j
End Sub
'5潰脤isa95腔tableName腔逄楊跡宒
Sub CHECK_ISA95_TABLE_SYNTAX()
    Dim Reg As New RegExp
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim m As Integer, result As Boolean
    Dim i As Integer, j As Integer, n As Integer
    Dim content As String, content1 As String
    Dim arrContent As Variant
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    n = xlErrSheet.[B65535].End(xlUp).Row + 1
    
    With Reg
    .Global = True
    .IgnoreCase = True
    .Pattern = "[A-Za-z]+\.[A-Za-z]+=\[[A-Za-z]+\]"
    End With
    
    m = xlWorksheet1.[T65535].End(xlUp).Row
    For i = 4 To m
        content = xlWorksheet1.Cells(i, 20)
        arrContent = Split(content, Chr(10))
        For j = 0 To UBound(arrContent)
            content1 = arrContent(j)
            result = Reg.Test(content1)
            If result = False Then
                xlWorksheet1.Cells(i, 20).Interior.ColorIndex = 3
                ' 森揭迡惆劑
                xlErrSheet.Cells(n, 2) = "CHECK_ISA95_TABLE_SYNTAX"
                xlErrSheet.Cells(n, 3) = "tableInfo腔tableName跡宒祥勤,cells(" & i & ",20)渣昫,祫屾�狐�漪 ISA桶跡靡想.�硩酵�想=�硩閥�"
                n = n + 1
                Exit For
            End If
        Next j
    Next i
End Sub
'6 統蕉硉郖沓楊潰聆
Sub CHECK_COLUMN_VALUE_DOMAIN()
    Dim Reg As New RegExp
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim m As Integer, i As Integer, n As Integer
    Dim content As String, content1 As String
    Dim result As Boolean

    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    n = xlErrSheet.[E65535].End(xlUp).Row + 1
    
    With Reg
    .Global = True
    .IgnoreCase = True
    .Pattern = "\[[A-Za-z]+\]\.\[[A-Za-z]+\]\.\[[A-Za-z]+\]\.\[[A-Za-z]+\]\.\[[A-Za-z]+\]\.\[[A-Za-z]+\]"
    End With
    '[炵緙ID].[揃蹋�閹D].[銼猁ID].[桶跡ID].[�硩膏D].[ColValue]

      
    m = xlWorksheet1.[].End(xlUp).Row
    For i = 4 To m
        content = Trim(xlWorksheet1.Cells(i, 8))
        If content = "C" Then
            '
        ElseIf content = "FID" Then
            '潰脤鞠僇宒
            content1 = xlWorksheet1.Cells(i, 27)
            result = Reg.Test(content1)
            If result = False Then
                'MsgBox "FID勤茼腔統蕉硉郖鞠僇宒ぐ迡渣昫"
                xlErrSheet.Cells(n, 5) = "CHECK_COLUMN_VALUE_DOMAIN"
                xlErrSheet.Cells(n, 6) = "ColumnInfo腔C⑹跡宒祥勤,鞠僇宒ぐ迡渣昫"
                n = n + 1
            End If
        Else
            'nothing to do
        End If
    Next i
    
End Sub
'7 潰聆嬤瘍岆瘁勤茼
Sub CHECK_SQUARE_BREAK_PAIR()
    Dim arrTableInfo As Variant, arrColumnInfo1 As Variant, arrColumnInfo2 As Variant
    Dim i As Integer, j1 As Integer, j2 As Integer
    Dim m As Integer, count1 As Integer, count2 As Integer
    Dim content As String, content1 As String, content2 As String, x As String
    Dim n As Integer, n1 As Integer, n2 As Integer, n3 As Integer
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    
    n = xlWorksheet1.[T65535].End(xlUp).Row
    n1 = xlWorksheet2.[T65535].End(xlUp).Row
    n2 = xlErrSheet.[B65535].End(xlUp).Row + 1
    n3 = xlErrSheet.[E65535].End(xlUp).Row + 1
    
    '潰聆tableInfo腔tableName腔嬤瘍岆瘁勤備
    For i = 4 To n Step 1
        count1 = 0
        count2 = 0
        content = xlWorksheet1.Cells(i, 20)
        arrTableInfo = Split(content, Chr(10))
        For j1 = 0 To UBound(arrTableInfo)
           '瓚剿藩俴腔囀�暌那籤閥�
            m = Len(arrTableInfo(j1))
            For j2 = 1 To m
            '涴爵衄恀枙,遜猁婓旃噶珨狟
                x = Mid(arrTableInfo(j1), j2, 1)
                If x = "[" Then
                   count1 = count1 + 1
                ElseIf x = "]" Then
                   count2 = count2 + 1
                Else
                End If
            Next j2
            If count1 <> count2 Then
                xlErrSheet.Cells(n2, 2) = "CHECK_SQUARE_BREAK_PAIR"
                xlErrSheet.Cells(n2, 3) = "TableInfo 腔B⑹TableName ,撈cells(" & i & ",20) �硩����楺倗釔依�祥傖�忙盈�甡笢嬤��傖�考��t沓��﹝"
                xlWorksheet1.Cells(i, 20).Interior.ColorIndex = 3
                n2 = n2 + 1
                Exit For
            End If
        Next j1
    Next i
    
    '潰聆columnInfo勤茼腔桶跡戲弇腔嬤瘍岆瘁勤備
    For i = 4 To n1 Step 1
        count1 = 0
        count2 = 0
        
        content = xlWorksheet2.Cells(i, 20)
        arrColumnInfo1 = Split(content, Chr(10))
        For j1 = 0 To UBound(arrColumnInfo1)
            m = Len(arrColumnInfo1(j1))
            For j2 = 1 To m
                x = Mid(arrColumnInfo1(j1), j2, 1)
                If x = "[" Then
                   count1 = count1 + 1
                ElseIf x = "]" Then
                   count2 = count2 + 1
                Else
                End If
            Next j2
            If count1 <> count2 Then
                xlErrSheet.Cells(n3, 5) = "CHECK_SQUARE_BREAK_PAIR"
                xlErrSheet.Cells(n3, 6) = "ColumnInfo 腔B⑹桶跡戲弇靡備 ,撈cells(" & i & ",20) �硩����楺倗釔依�祥傖�忙盈�甡笢嬤��傖�考��t沓��﹝"
                xlWorksheet2.Cells(i, 20).Interior.ColorIndex = 3
                n3 = n3 + 1
                Exit For
            End If
        Next j1
    Next i
    
     '潰聆columnInfo勤茼腔戲弇靡備腔嬤瘍岆瘁勤備
    For i = 4 To n1 Step 1
        count1 = 0
        count2 = 0
        
        content = xlWorksheet2.Cells(i, 21)
        arrColumnInfo2 = Split(content, Chr(10))
        For j1 = 0 To UBound(arrColumnInfo2)
            m = Len(arrColumnInfo2(j1))
            For j2 = 1 To m
                x = Mid(arrColumnInfo2(j1), j2, 1)
                If x = "[" Then
                   count1 = count1 + 1
                ElseIf x = "]" Then
                   count2 = count2 + 1
                Else
                End If
            Next j2
            If count1 <> count2 Then
                xlErrSheet.Cells(n3, 5) = "CHECK_SQUARE_BREAK_PAIR"
                xlErrSheet.Cells(n3, 6) = "ColumnInfo 腔B⑹戲弇靡備 ,撈cells(" & i & ",21) �硩����楺倗釔依�祥傖�忙盈�甡笢嬤��傖�考��t沓��﹝"
                xlWorksheet2.Cells(i, 21).Interior.ColorIndex = 3
                n3 = n3 + 1
                Exit For
            End If
        Next j1
    Next i
    
End Sub
'8潰脤UK
Sub CHECK_UK_SYNTAX()
    Dim m As Integer, n As Integer, i As Integer, j As Integer, j1 As Integer
    Dim xlWorksheet2 As Worksheet, xlErrSheet As Worksheet
    Dim content As String, content1 As String
    Dim arrContent As Variant, arrDemo As Variant
    Dim result As Boolean
     
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    arrDemo = Array("U", "U1", "U2", "U3", "U4", "U5", "U6", "U7", "U8", "U9")
    m = xlWorksheet2.[N65535].End(xlUp).Row
    n = xlErrSheet.[E65535].End(xlUp).Row + 1
    
    For i = 4 To m
        content = Trim(xlWorksheet2.Cells(i, 14))
        arrContent = Split(content, ",")
        For j = 0 To UBound(arrContent)
            result = False
            For j1 = 0 To UBound(arrDemo)
                If Trim(arrContent(j)) = arrDemo(j1) Then
                    result = True
                    Exit For
                End If
            Next j1
            If result = False Then
                xlErrSheet.Cells(n, 5) = "CHECK_UK_SYNTAX"
                xlErrSheet.Cells(n, 6) = "ColumnInfo 腔cells(" & i & ",14) UK�硩����搛鯓賢e悷, ��湖U���曊鉠M��蚚U1, U2"
                xlWorksheet2.Cells(i, 14).Interior.ColorIndex = 3
                n = n + 1
                Exit For
            End If
        Next j
    Next i

End Sub
'9潰脤FK
Sub CHECK_FK_SYNTAX()
    Dim m As Integer, i As Integer, j As Integer, j1 As Integer, n As Integer
    Dim xlWorksheet2 As Worksheet, xlErrSheet As Worksheet
    Dim content As String, content1 As String
    Dim arrContent As Variant, arrDemo As Variant
    Dim result As Boolean
     
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    arrDemo = Array("F", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9")
    m = xlWorksheet2.[O65535].End(xlUp).Row
    n = xlErrSheet.[E65535].End(xlUp).Row + 1
    
    For i = 4 To m
        content = Trim(xlWorksheet2.Cells(i, 15))
        arrContent = Split(content, ",")
        For j = 0 To UBound(arrContent)
            result = False
            For j1 = 0 To UBound(arrDemo)
                If Trim(arrContent(j)) = arrDemo(j1) Then
                    result = True
                    Exit For
                End If
            Next j1
            If result = False Then
                xlErrSheet.Cells(n, 5) = "CHECK_FK_SYNTAX"
                xlErrSheet.Cells(n, 6) = "ColumnInfo 腔A⑹ ,撈cells(" & i & ",15) FK�硩����搛鯓賢e悷, ��湖F���曊鉠M��蚚F1,F2"
                n = n + 1
                xlWorksheet2.Cells(i, 15).Interior.ColorIndex = 3
                Exit For
            End If
        Next j
    Next i
End Sub
'10 潰聆tableinfo 腔isa95勤茼腔戲弇
Sub CHECK_ISA95_TABLENAME_VAR()
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim m As Integer, n As Integer, i As Integer, j As Integer
    Dim x As Integer, y As Integer
    Dim content As String, content1 As String, StrOut As String
    Dim arrTable As Variant
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    n = xlErrSheet.[B65535].End(xlUp).Row + 1

    m = xlWorksheet1.[T65535].End(xlUp).Row
    For i = 4 To m
        content = xlWorksheet1.Cells(i, 20)
        arrTable = Split(content, Chr(10))
        For j = 0 To UBound(arrTable)
            content1 = arrTable(j)
            x = InStr(content1, "[") + 1
            y = InStr(content1, "]")
            StrOut = Mid(content1, x, y - x)
            'Debug.Print StrOut
            If Trim(StrOut) = "TableName" Then
               ' MsgBox "niebi1"
            ElseIf Trim(StrOut) = "TableComment" Then
               ' MsgBox "niebi2"
            Else
                xlErrSheet.Cells(n, 2) = "CHECK_ISA95_TABLENAME_VAR"
                xlErrSheet.Cells(n, 3) = "TableInfo 腔cells(" & i & ",20) ,森�硩��H褫妏蚚[TableName],[TableComment] ���絕���"
                n = n + 1
                xlWorksheet1.Cells(i, 20).Interior.ColorIndex = 3
                Exit For
            End If
        Next j
        
    Next i
End Sub
'11 潰脤columnInfo腔isa95勤茼腔戲弇
Sub CHECK_ISA95_COLUMNNAME_VAR()
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim m As Integer, n As Integer, i As Integer, j1 As Integer, j2 As Integer, x As Integer, y As Integer
    Dim StrOut As String, content As String, content1 As String
    Dim arrTable As Variant, arrContent As Variant
    Dim result As Boolean
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    arrContent = Array("TableName", "TableComment", "ColName", "ColValue", "ColComment", "ValueDomain", "Null")
    n = xlErrSheet.[E65535].End(xlUp).Row + 1
    m = xlWorksheet2.[T65535].End(xlUp).Row
    
    For i = 4 To m
        content = xlWorksheet2.Cells(i, 20)
        arrTable = Split(content, Chr(10))
        For j1 = 0 To UBound(arrTable)
            result = False
            For j2 = 0 To UBound(arrContent)
               
                content1 = arrTable(j1)
                x = InStr(content1, "[") + 1
                y = InStr(content1, "]")
                
                If x = 1 Then
                    '森揭衄恀枙,絞扂腔囀�楺陏閨釔那籥劗敦�
                    result = True
                    Exit For
                Else
                    StrOut = Mid(content1, x, y - x)
                    If Trim(StrOut) = arrContent(j2) Then
                        result = True
                        Exit For
                    End If
                End If
            Next j2
            If result = False Then
                xlErrSheet.Cells(n, 5) = "CHECK_ISA95_COLUMNNAME_VAR"
                xlErrSheet.Cells(n, 6) = "ColumnInfo 腔撈cells(" & i & ",20) ,森�硩��H褫妏蚚[TableName],[TableComment][ColName],[ColValue],[ColComment]���絕���"
                n = n + 1
                xlWorksheet2.Cells(i, 20).Interior.ColorIndex = 3
                Exit For
            End If
        Next j1
    Next i
End Sub
'12 睿11竭眈輪
Sub CHECK_ISA95_COLUMNVALUE_VAR()
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim m As Integer, i As Integer, j1 As Integer, j2 As Integer
    Dim x As Integer, y As Integer, n As Integer
    Dim StrOut As String, content As String, content1 As String
    Dim arrTable As Variant, arrContent As Variant
    Dim result As Boolean
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    arrContent = Array("TableName", "TableComment", "ColName", "ColValue", "ColComment", "ValueDomain", "Null")
    n = xlErrSheet.[E65535].End(xlUp).Row + 1
    m = xlWorksheet2.[U65535].End(xlUp).Row
    
    For i = 4 To m
        content = xlWorksheet2.Cells(i, 21)
        arrTable = Split(content, Chr(10))
        For j1 = 0 To UBound(arrTable)
            For j2 = 0 To UBound(arrContent)
                result = False
                content1 = arrTable(j1)
                x = InStr(content1, "[") + 1
                y = InStr(content1, "]")
                StrOut = Mid(content1, x, y - x)
                
                If Trim(StrOut) = arrContent(j2) Then
                    result = True
                    Exit For
                End If
            Next j2
            If result = False Then
                xlErrSheet.Cells(n, 5) = "CHECK_ISA95_COLUMNVALUE_VAR"
                xlErrSheet.Cells(n, 6) = "ColumnInfo 腔cells(" & i & ",21) ,森�硩��H褫妏蚚[TableName],[TableComment][ColName],[ColValue],[ColComment]���絕���"
                n = n + 1
                xlWorksheet2.Cells(i, 21).Interior.ColorIndex = 3
                Exit For
            End If
        Next j1
        
    Next i
End Sub
'13潰脤table腔跪戲祥褫笭葩,跪等啋跡囀�暔斯課酴藈�誕.
Sub CHECK_TABLE_DUP()
    Dim xlWorksheet1 As Worksheet
    Dim xlErrSheet As Worksheet
    Dim n As Integer, j1 As Integer, j2 As Integer, x As Integer
    Dim m1 As String, m2 As String, m3 As String, m4 As String
    Dim n1 As String, n2 As String, n3 As String, n4 As String
    Dim resultA As String, resultB As String
    
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    n = xlWorksheet1.[B65535].End(xlUp).Row
    x = xlErrSheet.[B65535].End(xlUp).Row + 1
    
    For j1 = 4 To n
        m1 = Trim(xlWorksheet1.Cells(j1, 2))
        m2 = Trim(xlWorksheet1.Cells(j1, 3))
        m3 = Trim(xlWorksheet1.Cells(j1, 4))
        m4 = Trim(xlWorksheet1.Cells(j1, 5))
        resultA = m1 & m2 & m3 & m4
        'Debug.Print resultA
        For j2 = 4 To n
            n1 = Trim(xlWorksheet1.Cells(j2, 2))
            n2 = Trim(xlWorksheet1.Cells(j2, 3))
            n3 = Trim(xlWorksheet1.Cells(j2, 4))
            n4 = Trim(xlWorksheet1.Cells(j2, 5))
            resultB = n1 & n2 & n3 & n4
            'Debug.Print resultB
            If (resultA = resultB) And (j1 <> j2) Then
                xlErrSheet.Cells(x, 2) = "CHECK_TABLE_DUP"
                xlErrSheet.Cells(x, 3) = "tableInfo腔A⑹菴" & j1 & "俴迵" & j2 & "俴笭恚隅膽ㄛ���z脤TableInfo笢[TableInfo-炵緙�e]+[TableInfo-DB NAME] +[TableInfo-DB Schema Name]+[TableInfo-DB Tablename]眳瞎磁﹝"
                x = x + 1
                xlWorksheet1.Cells(j2, 2).Interior.ColorIndex = 3
                xlWorksheet1.Cells(j2, 3).Interior.ColorIndex = 3
                xlWorksheet1.Cells(j2, 4).Interior.ColorIndex = 3
                xlWorksheet1.Cells(j2, 5).Interior.ColorIndex = 3
            End If
        Next j2
    Next j1
End Sub
'14 潰脤column腔跪戲祥褫笭葩
Sub CHECK_COLUMN_DUP()
    Dim xlWorksheet2 As Worksheet
    Dim xlErrSheet As Worksheet
    Dim x As Integer, y As Integer, j1 As Integer, j2 As Integer
    Dim m1 As String, m2 As String, m3 As String, m4 As String, m5 As String
    Dim n1 As String, n2 As String, n3 As String, n4 As String, n5 As String
    Dim resultA As String, resultB As String
    
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    x = xlWorksheet2.[B65535].End(xlUp).Row
    y = xlErrSheet.[E65535].End(xlUp).Row + 1
    
    For j1 = 4 To x
        m1 = Trim(xlWorksheet2.Cells(j1, 2))
        m2 = Trim(xlWorksheet2.Cells(j1, 3))
        m3 = Trim(xlWorksheet2.Cells(j1, 4))
        m4 = Trim(xlWorksheet2.Cells(j1, 5))
        m5 = Trim(xlWorksheet2.Cells(j1, 6))
        resultA = m1 & m2 & m3 & m4 & m5
        'Debug.Print resultA
        For j2 = 4 To x
            n1 = Trim(xlWorksheet2.Cells(j2, 2))
            n2 = Trim(xlWorksheet2.Cells(j2, 3))
            n3 = Trim(xlWorksheet2.Cells(j2, 4))
            n4 = Trim(xlWorksheet2.Cells(j2, 5))
            n5 = Trim(xlWorksheet2.Cells(j2, 6))
            resultB = n1 & n2 & n3 & n4 & n5
            'Debug.Print resultB
            If (resultA = resultB) And (j1 <> j2) Then
                xlErrSheet.Cells(y, 5) = "CHECK_COLUMN_DUP"
                xlErrSheet.Cells(y, 6) = "ColumnInfo腔A⑹菴" & j1 & "俴迵" & j2 & "俴笭葩隅膽ㄛ���z脤ColumnInfo笢[ColumnInfo-炵緙�e]+[ColumnInfo-DB NAME] + [ColumnInfo-DB Schema Name] +[ColumnInfo-DB Tablename]摯[ColumnInfo-DB Columnname]眳瞎磁﹝"
                y = y + 1
                xlWorksheet2.Cells(j2, 2).Interior.ColorIndex = 3
                xlWorksheet2.Cells(j2, 3).Interior.ColorIndex = 3
                xlWorksheet2.Cells(j2, 4).Interior.ColorIndex = 3
                xlWorksheet2.Cells(j2, 5).Interior.ColorIndex = 3
                xlWorksheet2.Cells(j2, 6).Interior.ColorIndex = 3
            End If
        Next j2
    Next j1
  
End Sub
'15壺賸杻隅撓俴,む坻等啋跡腔囀�搋撞遛圴И閛�
Sub CHECK_MULTILINE_SUPPORT()

    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim arrTable As Variant, arrColumn As Variant, arrContent As Variant, arrContent1 As Variant
    Dim n1 As Integer, n2 As Integer, i1 As Integer, i2 As Integer, j1 As Integer, j2 As Integer
    Dim x As Integer, y As Integer
    Dim content As String, content1 As String
    
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    
    arrTable = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26)
    arrColumn = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 24, 25, 26)
    n1 = xlWorksheet1.[B65535].End(xlUp).Row
    n2 = xlWorksheet2.[B65535].End(xlUp).Row
    x = xlErrSheet.[B65535].End(xlUp).Row + 1
    y = xlErrSheet.[E65535].End(xlUp).Row + 1
      
    For i1 = 4 To n1
        For j1 = 0 To UBound(arrTable)
            content = xlWorksheet1.Cells(i1, arrTable(j1))
            arrContent = Split(content, Chr(10))
            If UBound(arrContent) >= 1 Then
                xlErrSheet.Cells(x, 2) = "CHECK_MULTILINE_SUPPORT"
                xlErrSheet.Cells(x, 3) = "TableInfo腔cells(" & i1 & "," & arrTable(j1) & "),森�硩����搚閩彸S嗣俴(Alter+Enter眳�Q俴)揃蹋, ��党淏. "
                x = x + 1
                xlWorksheet1.Cells(i1, arrTable(j1)).Interior.ColorIndex = 3
            End If
        Next j1
    Next i1
    
    For i2 = 4 To n2
        For j2 = 0 To UBound(arrColumn)
            content1 = xlWorksheet2.Cells(i2, arrColumn(j2))
            arrContent1 = Split(content1, Chr(10))
            If UBound(arrContent1) >= 1 Then
                xlErrSheet.Cells(y, 5) = "CHECK_MULTILINE_SUPPORT"
                xlErrSheet.Cells(y, 6) = "ColumnInfo腔cells(" & i2 & "," & arrColumn(j2) & "),森�硩����搚閩彸S嗣俴(Alter+Enter眳�Q俴)揃蹋, ��党淏. "
                y = y + 1
                xlWorksheet2.Cells(i2, arrColumn(j2)).Interior.ColorIndex = 3
                
            End If
        Next j2
    Next i2

End Sub
'16 潰脤table桶勤茼善column岆瘁衄�捩�,穢迡
Sub CHECK_COLUMN_UNDEF()
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim arrTable As Variant, arrColumn As Variant
    Dim a As Integer, b As Integer, x As Integer
    Dim n1 As Integer, n2 As Integer, result As Boolean
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    a = xlWorksheet1.[E65535].End(xlUp).Row
    b = xlWorksheet2.[E65535].End(xlUp).Row
    x = xlErrSheet.[B65535].End(xlUp).Row + 1
    
    For n1 = 4 To a
        '暮翹郔綴賦彆
        result = False
        For n2 = 4 To b
            If Trim(xlWorksheet1.Cells(n1, 5)) = Trim(xlWorksheet2.Cells(n2, 5)) Then
                result = True
                Exit For
            End If
        Next n2
        If (result = False) Then
            xlErrSheet.Cells(x, 2) = "CHECK_COLUMN_UNDEF"
            xlErrSheet.Cells(x, 3) = "TableInfo 腔 cells(" & n1 & ",5),桶跡撩隅膽ㄛ筍�硩銜普阪xㄛ���z脤ColumnInfo岆瘁衄隅膽��桶跡�硩����搳�"
            x = x + 1
            xlWorksheet1.Cells(n1, 5).Interior.ColorIndex = 3
        End If
    Next n1
End Sub
'17潰脤Column桶勤茼善table岆瘁衄ぐ迡渣昫
Sub CHECK_TABLE_UNDEF()

    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim arrTable As Variant, arrColumn As Variant
    Dim a As Integer, b As Integer, x As Integer
    Dim n1 As Integer, n2 As Integer, result As Boolean
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    a = xlWorksheet1.[E65535].End(xlUp).Row
    b = xlWorksheet2.[E65535].End(xlUp).Row
    x = xlErrSheet.[E65535].End(xlUp).Row + 1
       
    For n2 = 4 To b
        '暮翹郔綴賦彆
        result = False
        For n1 = 4 To a
            If Trim(xlWorksheet2.Cells(n2, 5)) = Trim(xlWorksheet1.Cells(n1, 5)) Then
                result = True
                Exit For
            End If
        Next n1
        If (result = False) Then
            xlErrSheet.Cells(x, 5) = "CHECK_TABLE_UNDEF"
            xlErrSheet.Cells(x, 6) = "ColumnInfo 腔cells(" & n2 & ",5),�硩遞熄阪xㄛ筍桶跡帤隅膽ㄛ���z脤TableInfo岆瘁衄隅膽��桶跡���搳�"
            x = x + 1
            xlWorksheet2.Cells(n2, 5).Interior.ColorIndex = 3
        End If
    Next n2
End Sub
'18潰聆domain睿subdomain腔俴杅岆瘁眈肮
Function CHECK_ISA95_DOMAIN_SUBDOMAIN_EQUAL_ROWS(xlWorkbook As Workbook) As Boolean
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim n1 As String, m1 As String, i As Integer, x1 As Integer, y1 As Integer, b1 As Boolean
    Dim n2 As String, m2 As String, j As Integer, x2 As Integer, y2 As Integer, b2 As Boolean
    Dim arrTable As Variant, arrColumn As Variant
    Dim a As Integer, b As Integer
    
    Set xlErrSheet = xlWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = xlWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = xlWorkbook.Sheets("ColumnInfo")
    a = xlErrSheet.[B65535].End(xlUp).Row
    b = xlErrSheet.[E65535].End(xlUp).Row
   
    '聆彸table腔domain睿subdomain
    For i = 4 To xlWorksheet1.[V65535].End(xlUp).Row
        n1 = xlWorksheet1.Cells(i, 21)
        m1 = xlWorksheet1.Cells(i, 22)
        Trim (n1)
        Trim (m1)
        arrTable = Split(m1, Chr(10))
        x1 = UBound(arrTable)
        arrColumn = Split(n1, Chr(10))
        y1 = UBound(arrColumn)
        If x1 <> y1 Then
            b1 = False
            xlErrSheet.Cells(a, 2) = "CHECK_ISA95_DOMAIN_SUBDOMAIN_EQUAL_ROWS"
            xlErrSheet.Cells(a, 3) = "TableInfo���玥蔥鯧SA95腔菴" & i & "俴 Domain摯Sub Domain眳衄虴揃蹋蹈���肢鉞�"
            a = a + 1
        Else
            b1 = True
        End If
    Next i
    
    '聆彸column腔domain睿subdomain
    For j = 4 To xlWorksheet2.[V65535].End(xlUp).Row
        n2 = xlWorksheet2.Cells(j, 22)
        m2 = xlWorksheet2.Cells(j, 23)
        Trim (n2)
        Trim (m2)
        arrTable = Split(m2, Chr(10))
        x2 = UBound(arrTable)
        arrColumn = Split(n2, Chr(10))
        y2 = UBound(arrColumn)
        If x2 <> y2 Then
            b2 = False
            xlErrSheet.Cells(a, 5) = "CHECK_ISA95_DOMAIN_SUBDOMAIN_EQUAL_ROWS"
            xlErrSheet.Cells(a, 6) = "ColumnInfo���玥蔥鯧SA95腔菴" & i & "俴 Domain摯Sub Domain眳衄虴揃蹋蹈���肢鉞�"
            b = b + 1
        Else
            b2 = True
        End If
    Next j
    If b1 = True And b2 = True Then
        CHECK_ISA95_DOMAIN_SUBDOMAIN_EQUAL_ROWS = True
    Else
        CHECK_ISA95_DOMAIN_SUBDOMAIN_EQUAL_ROWS = False
    End If
End Function
'Activity勤茼腔subDomain扽俶
Sub CheckSubDomain()
    Dim result As Boolean, finalResult As Boolean
    Dim m As Integer, n As Integer, numAct As Integer, numActRes As Integer, arrNum As Integer
    Dim i1 As Integer, i2 As Integer, j2 As Integer, j1 As Integer, y As Integer
    Dim NumActivity As Integer, DomainCells As String, SubDomainCells As String, DomainFirst As String, SubDomainFirst As String
    Dim xlErrSheet As Worksheet
    Dim xlWorksheet1 As Worksheet
    Dim xlWorksheet2 As Worksheet
    Dim xlWorksheet3 As Worksheet
    Dim arrActivity, arrActRes, arrRes, arrDomain As Variant, arrSubDomain As Variant, arrDomain1 As Variant
    
    Set xlErrSheet = ThisWorkbook.Sheets("CheckReport")
    Set xlWorksheet1 = ThisWorkbook.Sheets("TableInfo")
    Set xlWorksheet2 = ThisWorkbook.Sheets("ColumnInfo")
    Set xlWorksheet3 = ThisWorkbook.Sheets("Values")
    
    '覃蚚18籵蚚髡夔
    result = CHECK_ISA95_DOMAIN_SUBDOMAIN_EQUAL_ROWS(ThisWorkbook)
    If result = False Then
        MsgBox "③珂潰脤TableInfo腔domain睿subdomain腔囀��,俴杅祥珨祡"
    End If
    
    '瓚剿賦彆
    n = xlWorksheet1.[U65535].End(xlUp).Row
    y = xlErrSheet.[B65535].End(xlUp).Row + 1
    For i2 = 4 To n
        
        'domain爵醱腔囀��
        DomainCells = Trim(xlWorksheet1.Cells(i2, 21))
        arrDomain = Split(DomainCells, Chr(10))
       ' Debug.Print arrDomain(0)
       ' Debug.Print arrDomain(1)
      '  arrDomain1 = Split(DomainCells, Chr(13))
      '  Debug.Print arrDomain1(0)
      '  Debug.Print arrDomain1(1)
        
            
        'subdomain爵醱腔囀��
        SubDomainCells = Trim(xlWorksheet1.Cells(i2, 22))
        arrSubDomain = Split(SubDomainCells, Chr(10))
       ' Debug.Print arrSubDomain(0)
        
        arrNum = UBound(arrDomain) + 1
        If arrNum = 1 Then
            DomainFirst = arrDomain(0)
            SubDomainFirst = arrSubDomain(0)
            If (arrDomain(0) = "Activity") Then
                finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY(DomainFirst, SubDomainFirst)
            ElseIf (arrDomain(0) = "ActivityResource") Then
                finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY_RESOURCE(DomainFirst, SubDomainFirst)
            ElseIf (arrDomain(0) = "Resource") Then
                finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_RESOURCE(DomainFirst, SubDomainFirst)
            Else
                finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_PROCESS(DomainFirst, SubDomainFirst)
            End If
           
        Else
            For j1 = 0 To UBound(arrDomain) Step 1
                DomainFirst = arrDomain(j1)
                SubDomainFirst = arrSubDomain(j1)
                '瓚剿
                If (arrDomain(j1) = "Activity") Then
                    finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY(DomainFirst, SubDomainFirst)
                ElseIf (arrDomain(j1) = "ActivityResource") Then
                    finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY_RESOURCE(DomainFirst, SubDomainFirst)
                ElseIf (arrDomain(j1) = "Resource") Then
                    finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_RESOURCE(DomainFirst, SubDomainFirst)
                Else
                    finalResult = CHECK_ISA95_DOMAIN_SUBDOMAIN_PROCESS(DomainFirst, SubDomainFirst)
                End If
                 '扂猁瓚剿,�蝜�謗棒賦彆珨跺true,珨跺false崋繫域
                If finalResult = False Then Exit For
             Next j1
            
        End If
        If (finalResult = False) Then
            xlErrSheet.Cells(y, 2) = "CheckSubDomain"
            xlErrSheet.Cells(y, 3) = "TableInfo 腔 SubDomain,撈Cells(" & i2 & ", 22) 揭ぐ迡渣昫"
            y = y + 1
            xlWorksheet1.Cells(i2, 22).Interior.ColorIndex = 3
        Else
            
        End If
        
    Next i2
End Sub
'19 domain岆activity奀釬掀誕
Function CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY(abc As String, abd As String) As Boolean
    Dim i1 As Integer, p As Integer, j1 As Integer, numAct As Integer
    Dim arrActivity, ActivityValues As Variant, cellValueTrimmed As Variant, cellValue As Variant
    Dim xlWorksheet3 As Worksheet

    Set xlWorksheet3 = ThisWorkbook.Sheets("Values")
    CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY = False
    numAct = xlWorksheet3.[V65535].End(xlUp).Row
    ActivityValues = xlWorksheet3.Range("Activity").Value
    
    For j1 = 1 To numAct Step 1
        For Each cellValue In ActivityValues
            If abc = "Activity" Then
                If (abd = Trim(cellValue)) Then
                    CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY = True
                    Exit For
                End If
            End If
         Next cellValue
    Next j1
End Function
'20 domain岆activityResource奀釬掀誕
Function CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY_RESOURCE(abc As String, abd As String) As Boolean
    Dim i1 As Integer, p As Integer, j1 As Integer, numActRos As Integer
    Dim arrActRos, ActivityResourceValues As Variant, cellValue As Variant
    Dim xlWorksheet3 As Worksheet

    Set xlWorksheet3 = ThisWorkbook.Sheets("Values")
    CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY_RESOURCE = False
    numActRos = xlWorksheet3.[W65535].End(xlUp).Row
    ActivityResourceValues = xlWorksheet3.Range("Activity").Value
    
    For j1 = 1 To numActRos Step 1
        For Each cellValue In ActivityResourceValues
            If abc = "ActivityResource" Then
                If (abd = Trim(cellValue)) Then
                    CHECK_ISA95_DOMAIN_SUBDOMAIN_ACTIVITY_RESOURCE = True
                    Exit For
                End If
            End If
        Next cellValue
    Next j1
End Function
'21 domain岆Resource奀釬掀誕
Function CHECK_ISA95_DOMAIN_SUBDOMAIN_RESOURCE(abc As String, abd As String) As Boolean
    Dim j1 As Integer
    Dim arrRos

    arrRos = Array("Personnel", "Equipment", "Material", "PhysicalAsset")
    CHECK_ISA95_DOMAIN_SUBDOMAIN_RESOURCE = False
    
    For j1 = 0 To UBound(arrRos) Step 1
        If abc = "Resource" Then
            If (abd = arrRos(j1)) Then
                CHECK_ISA95_DOMAIN_SUBDOMAIN_RESOURCE = True
                Exit For
            End If
        End If
    Next j1
End Function
'22 domain岆process奀釬掀誕
Function CHECK_ISA95_DOMAIN_SUBDOMAIN_PROCESS(abc As String, abd As String) As Boolean
    Dim ji As Integer

    CHECK_ISA95_DOMAIN_SUBDOMAIN_PROCESS = False
    
    If abc = "Process" Then
        If (abd = "Process") Then
            MsgBox "Processぁ饜傖髡,潰脤狟珨跺"
           CHECK_ISA95_DOMAIN_SUBDOMAIN_PROCESS = True
        End If
    End If
End Function
'鳳�＞�
Sub getValue()
    Dim valuesWorksheet As Worksheet
    Dim tableRelationTypeValues As Variant
    Dim cellValue As Variant
    Dim cellValueTrimmed As String
    
    Set valuesWorksheet = ThisWorkbook.Sheets("Values")
        
    tableRelationTypeValues = valuesWorksheet.Range("Activity").Value
    For Each cellValue In tableRelationTypeValues
        cellValueTrimmed = Trim(cellValue)
        If cellValueTrimmed = "" Then
            Debug.Print "DoNothing"
        Else
            Debug.Print cellValueTrimmed
        End If
    Next cellValue

End Sub
Function Check_IT_FILE_Base(xlWorkbook As Workbook, SystemTables() As SYSTEM_TBL) As Boolean

    
End Function