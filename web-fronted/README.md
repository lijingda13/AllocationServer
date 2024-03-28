# web-fronted

## Installation

Install the application dependencies by running:

```sh
npm install
```

## Development

Start the application in development mode by running:

```sh
npm run dev
```

## Production

Build the application in production mode by running:

```sh
npm run build
```

## DataProvider

The included data provider use [ra-data-json-server](https://github.com/marmelab/react-admin/tree/master/packages/ra-data-json-server). 

## Project structure

```
├── README.md
├── index.html // the entry HTML file
├── package-lock.json //lockfile: ensuring consistent dependency versions across installations
├── package.json // the configuration file 
├── prettier.config.js // specify code formatting rules
├── public // public assets
│   ├── favicon.ico
│   └── manifest.json
├── src
│   ├── auth  //  files related to authentication
│   │   ├── MyLoginPage.tsx // Custom login page component
│   │   └── authProvider.ts // Authentication provider 
│   ├── index.tsx // entry component
│   ├── pages // page components and data processing related files
│   │   ├── App.tsx
│   │   ├── Dashboard.tsx
│   │   ├── dataProvider.ts
│   │   ├── layout.tsx
│   │   ├── projects.tsx
│   │   ├── users.tsx
│   │   └── vite-env.d.ts
│   └── share
│       ├── env.ts // Environment variable 
│       ├── styles.css // Shared style file
│       └── url.ts // Shared URL file
├── tsconfig.json
└── vite.config.ts
```